package com.riwi.assessment.infrastructure.adapter.out.persistence;

import com.riwi.assessment.domain.model.Task;
import com.riwi.assessment.domain.port.out.TaskRepositoryPort;
import com.riwi.assessment.infrastructure.adapter.out.persistence.mapper.TaskPersistenceMapper;
import com.riwi.assessment.infrastructure.adapter.out.persistence.repository.JpaTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository repository;
    private final TaskPersistenceMapper mapper;

    @Override
    public Task save(Task task) {
        var entity = mapper.toEntity(task);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Task> findByProjectId(UUID projectId) {
        return repository.findByProjectIdAndDeletedFalse(projectId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countActiveTasksByProjectId(UUID projectId) {
        return repository.countByProjectIdAndCompletedFalseAndDeletedFalse(projectId);
    }
}