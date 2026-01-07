package com.riwi.assessment.domain.port.in;

import java.util.UUID;

public interface CompleteTaskUseCase {
    void complete(UUID taskId);
}