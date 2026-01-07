package com.riwi.assessment.infrastructure.adapter.out.persistence.mapper;

import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.infrastructure.adapter.out.persistence.entity.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectPersistenceMapper {
    public Project toDomain(ProjectEntity entity) {
        return Project.builder()
                .id(entity.getId())
                .ownerId(entity.getOwnerId())
                .name(entity.getName())
                .status(entity.getStatus())
                .deleted(entity.isDeleted())
                .build();
    }

    public ProjectEntity toEntity(Project domain) {
        return ProjectEntity.builder()
                .id(domain.getId())
                .ownerId(domain.getOwnerId())
                .name(domain.getName())
                .status(domain.getStatus())
                .deleted(domain.isDeleted())
                .build();
    }
}