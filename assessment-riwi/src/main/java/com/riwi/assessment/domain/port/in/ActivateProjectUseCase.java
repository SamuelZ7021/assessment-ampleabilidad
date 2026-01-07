package com.riwi.assessment.domain.port.in;

import java.util.UUID;

public interface ActivateProjectUseCase {
    void activate(UUID projectId);
}