package com.tui.cooplend.services;

import com.tui.cooplend.entities.AuditEntry;
import com.tui.cooplend.entities.User;
import com.tui.cooplend.repositories.AuditEntryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@AllArgsConstructor
public class AuditEntryService {
    private final AuditEntryRepository auditEntryRepository;

    public void record(String action, String entityType, Long entityId, String description){
        String actor = currentActor();
        User actorId = new User();
        auditEntryRepository.save(AuditEntry.builder()
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .actorId(actorId)
                .description(description)
                .build());
    }
    private String currentActor(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null){
            return "system";
        }
        return authentication.getName();
    }
}
