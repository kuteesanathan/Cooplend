package com.tui.cooplend.commonerrors;

/*
* Thrown when a request is well-formed but violates a domain/business rule
* Eg amount outside product limits, repayment exceeding balance.
* Mapped to HTTP 422 unprocessable Entity - the request was understood but
* cannot be carried out because of the current state of the domain.
* */
public class BusinessRuleViolationException extends RuntimeException{
    private final String code;

    public BusinessRuleViolationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessRuleViolationException(String message){
        this("BUSINESS_RULE_VIOLATION", message);
    }

    public String getCode() {
        return code;
    }
}
