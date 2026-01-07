package com.riwi.assessment.infrastructure.adapter.in.web.controller;

import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.port.in.ActivateProjectUseCase;
import com.riwi.assessment.domain.port.out.ProjectRepositoryPort;
import com.riwi.assessment.domain.port.out.CurrentUserPort;
import com.riwi.assessment.infrastructure.adapter.in.web.dto.request.ProjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectRepositoryPort projectRepository;
    private final ActivateProjectUseCase activateProjectUseCase;
    private final CurrentUserPort currentUserPort;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectRequest request) {
        Project project = Project.builder()
                .name(request.getName())
                .ownerId(currentUserPort.getCurrentUserId())
                .status(Project.ProjectStatus.DRAFT)
                .build();
        return ResponseEntity.ok(projectRepository.save(project));
    }

    @GetMapping
    public ResponseEntity<List<Project>> list() {
        return ResponseEntity.ok(projectRepository.findAllByOwnerId(currentUserPort.getCurrentUserId()));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable UUID id) {
        activateProjectUseCase.activate(id);
        return ResponseEntity.ok(Map.of("message", "Proyecto activado con éxito"));
    }
}