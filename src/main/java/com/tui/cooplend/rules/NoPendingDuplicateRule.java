package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class NoPendingDuplicateRule implements BusinessRule<EligibilityContext> {
    private static final String NAME = "NO_PENDING_DUPLICATE";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        if (context.hasPendingApplicationForSameProduct()){
            return RuleResult.fail(NAME, "Member already has a pending application for this product");
        }
        return RuleResult.pass(NAME);
    }
}
