package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class AmountWithinLimitsRule implements BusinessRule<EligibilityContext> {
    private static final String NAME = "AMOUNT_WITHIN_LIMITS";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        boolean withinLimits = context.requestedAmount().compareTo(context.productMinAmount()) >= 0 && context.requestedAmount().compareTo(context.productMaxAmount()) <= 0;
        if (!withinLimits){
            return RuleResult.fail(NAME, "Requested amount " + context.requestedAmount() + " is outside the product limit [" + context.productMinAmount() + ", " + context.productMaxAmount() + "]");
        }
        return RuleResult.pass(NAME);
    }
}
