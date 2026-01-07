package com.riwi.assessment.infrastructure.adapter.out.persistence.repository;

import com.riwi.assessment.infrastructure.adapter.out.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByProjectIdAndDeletedFalse(UUID projectId);
    long countByProjectIdAndCompletedFalseAndDeletedFalse(UUID projectId); // Regla de negocio 1
}