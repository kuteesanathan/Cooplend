package com.tui.cooplend.rules;

public interface BusinessRule<T> {
    RuleResult evaluate(T context);
}
