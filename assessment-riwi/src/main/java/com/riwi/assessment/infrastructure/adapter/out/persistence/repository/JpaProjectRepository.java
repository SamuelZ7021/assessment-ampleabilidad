package com.riwi.assessment.infrastructure.adapter.out.persistence.repository;

import com.riwi.assessment.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JpaProjectRepository extends JpaRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findByOwnerIdAndDeletedFalse(UUID ownerId);
}