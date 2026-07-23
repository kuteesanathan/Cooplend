package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class ProductActiveRule implements BusinessRule<EligibilityContext>{
    private static final String NAME = "PRODUCT_ACTIVE";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        if (!context.productActive()){
            return RuleResult.fail(NAME, "Loan product is not active");
        }
        return RuleResult.pass(NAME);
    }
}
