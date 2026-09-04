package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class NoOverDueLoanRule implements BusinessRule<EligibilityContext> {
    private static final String NAME = "NO_OVERDUE_LOAN";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        if (context.hasActiveLoanWithUnpaidBalance()){
            return RuleResult.fail(NAME, "Member has an active loan with an outstanding balance");
        }
        return RuleResult.pass(NAME);

    }
}
