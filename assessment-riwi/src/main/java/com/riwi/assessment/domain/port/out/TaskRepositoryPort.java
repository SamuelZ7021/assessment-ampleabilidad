package com.riwi.assessment.domain.port.out;

import com.riwi.assessment.domain.model.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    Task save(Task task);
    Optional<Task> findById(UUID id);
    List<Task> findByProjectId(UUID projectId);
    long countActiveTasksByProjectId(UUID projectId);
}