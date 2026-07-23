package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class TermsWithinLimitsRule implements BusinessRule<EligibilityContext> {
    private static final String NAME = "TERM_WITHIN_LIMITS";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        boolean withinLimits = context.requestedTermMonths() >= context.productMinTerm() && context.requestedTermMonths() <= context.productMaxTerm();
        if (!withinLimits){
            return RuleResult.fail(NAME, "Requested term " + context.requestedTermMonths() + " months id outside the product limit [" + context.productMinTerm() + ", " + context.productMaxTerm() + "]");
        }
        return RuleResult.pass(NAME);
    }
}
