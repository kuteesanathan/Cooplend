package com.tui.cooplend.commonerrors;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String code,
        String message,
        String path,
        List<FieldErrorItem> fieldErrorItems
) {
    public record FieldErrorItem(String field, String message){
    }
}
