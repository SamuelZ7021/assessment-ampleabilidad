package com.riwi.assessment.infrastructure.adapter.out.audit;

import com.riwi.assessment.domain.port.out.AuditLogPort;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ConsoleAuditAdapter implements AuditLogPort {
    @Override
    public void register(String action, UUID entityId) {
        System.out.println("[AUDIT LOG] Action: " + action + " | Entity ID: " + entityId);
    }
}