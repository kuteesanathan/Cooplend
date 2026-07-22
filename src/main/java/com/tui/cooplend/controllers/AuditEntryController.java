package com.tui.cooplend.controllers;

import com.tui.cooplend.dtos.AuditEntryResponse;
import com.tui.cooplend.repositories.AuditEntryRepository;
import lombok.AllArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit-entries")
@AllArgsConstructor
public class AuditEntryController {
    private final AuditEntryRepository auditEntryRepository;

    @GetMapping
    public ResponseEntity<Page<AuditEntryResponse>>list(Pageable pageable){
        Page page = (Page) auditEntryRepository.findAllByOrderByTimestampDesc(pageable).map(e -> new AuditEntryResponse(e.getId(), e.getAction(), e.getEntityType(), e.getEntityId(), e.getActorId().getId(), e.getTimestamp(), e.getDescription()));
        return ResponseEntity.ok(page);
    }
}
