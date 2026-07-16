package com.tui.cooplend.dtos;

import java.time.LocalDateTime;

public record AuditEntryResponse(
        Long id,
        String action,
        String entityType,
        Long entityId,
        Long actorId,
        LocalDateTime timestamp,
        String description
) {
}
