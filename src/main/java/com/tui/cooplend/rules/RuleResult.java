package com.tui.cooplend.rules;

public record RuleResult(boolean passed, String ruleName, String reason) {
    public static RuleResult pass(String ruleName){
        return new RuleResult(true, ruleName, null);

    }

    public static RuleResult fail(String ruleName, String reason){
        return new RuleResult(false, ruleName, reason);

    }
}
