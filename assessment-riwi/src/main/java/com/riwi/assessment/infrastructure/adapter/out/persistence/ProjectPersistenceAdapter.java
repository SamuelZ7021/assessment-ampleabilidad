package com.riwi.assessment.infrastructure.adapter.out.persistence;

import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.port.out.ProjectRepositoryPort;
import com.riwi.assessment.infrastructure.adapter.out.persistence.mapper.ProjectPersistenceMapper;
import com.riwi.assessment.infrastructure.adapter.out.persistence.repository.JpaProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements ProjectRepositoryPort {

    private final JpaProjectRepository repository;
    private final ProjectPersistenceMapper mapper;

    @Override
    public Project save(Project project) {
        var entity = mapper.toEntity(project);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Project> findAllByOwnerId(UUID ownerId) {
        return repository.findByOwnerIdAndDeletedFalse(ownerId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}