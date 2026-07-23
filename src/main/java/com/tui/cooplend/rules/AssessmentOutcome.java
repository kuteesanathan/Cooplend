package com.tui.cooplend.rules;

import java.util.List;

public record AssessmentOutcome(boolean eligible, List<RuleResult> ruleResults) {
    public String summarise(){
        return ruleResults.stream()
                .map(r -> r.ruleName() + "=" + (r.passed() ? "PASS" : "FAIL(" + r.reason() + ")"))
                .reduce((a,b) -> a + ";" + b)
                .orElse("no rules evaluated");
    }
}
