package com.tui.cooplend.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class EligibilityAssessmentService {
    private static final Logger log = LoggerFactory.getLogger(EligibilityAssessmentService.class);
    
    private final List<BusinessRule<EligibilityContext>> rules;
    private final TaskExecutor executor;
    private final long timeoutSeconds;


    public EligibilityAssessmentService(List<BusinessRule<EligibilityContext>> rules,
                                        @Qualifier("eligibilityExecutor") TaskExecutor executor,
                                        @Value("${cooplend.eligibility.rule.timeout-seconds:3}") long timeoutSeconds
    ) {
        this.rules = rules;
        this.executor = executor;
        this.timeoutSeconds = timeoutSeconds;
    }

    public AssessmentOutcome assess(EligibilityContext context){
        List<CompletableFuture<RuleResult>> futures = rules.stream().map(rule -> runWithTimeout(rule, context)).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        List<RuleResult> results = futures.stream().map(CompletableFuture::join).toList();
        boolean eligible = results.stream().allMatch(RuleResult::passed);

        return new AssessmentOutcome(eligible, results);
    }

    private CompletableFuture<RuleResult> runWithTimeout(BusinessRule<EligibilityContext> rule, EligibilityContext context){
        String ruleName = rule.getClass().getSimpleName();
        return CompletableFuture.supplyAsync(() -> rule.evaluate(context), executor)
                .orTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .handle((result, throwable) -> {
                    if (throwable==null){
                        return result;
                    }
                    String reason = throwable instanceof TimeoutException ? "Rule timed out after " + timeoutSeconds + "s" : "Rule threw an exception: " + throwable.getMessage();
                    log.warn("Eligibility rule {} did not complete normally: {}", ruleName, reason);

                    return RuleResult.fail(ruleName, reason);
                });
    }

}
