package com.tui.cooplend.rules;

import org.springframework.stereotype.Component;

@Component
public class MemberActiveRule implements BusinessRule<EligibilityContext> {
    private static final String NAME = "MEMBER_ACTIVE";

    @Override
    public RuleResult evaluate(EligibilityContext context) {
        if (!context.memberActive()) {
            return RuleResult.fail(NAME, "Member is not active(suspended members cannot apply)");
        }
        return RuleResult.pass(NAME);
    }
}
