package com.tui.cooplend.repositories;

import com.tui.cooplend.entities.AuditEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditEntryRepository extends JpaRepository<AuditEntry, Long> {
    List<AuditEntry> findByEntityTypeAndEntityId(String entityType, Long entityId);
    Page<AuditEntry> findAllByOrderByTimestampDesc(Pageable pageable);
}
