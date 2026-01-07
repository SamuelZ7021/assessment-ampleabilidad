package com.riwi.assessment.infrastructure.adapter.out.persistence.mapper;

import com.riwi.assessment.domain.model.Task;
import com.riwi.assessment.infrastructure.adapter.out.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskPersistenceMapper {

    public Task toDomain(TaskEntity entity) {
        if (entity == null) return null;

        return Task.builder()
                .id(entity.getId())
                .projectId(entity.getProjectId())
                .title(entity.getTitle())
                .completed(entity.isCompleted())
                .deleted(entity.isDeleted())
                .build();
    }

    public TaskEntity toEntity(Task domain) {
        if (domain == null) return null;

        return TaskEntity.builder()
                .id(domain.getId())
                .projectId(domain.getProjectId())
                .title(domain.getTitle())
                .completed(domain.isCompleted())
                .deleted(domain.isDeleted())
                .build();
    }
}