package com.riwi.assessment.infrastructure.adapter.in.web.controller;

import com.riwi.assessment.domain.model.Task;
import com.riwi.assessment.domain.port.in.CompleteTaskUseCase;
import com.riwi.assessment.domain.port.out.CurrentUserPort;
import com.riwi.assessment.domain.port.out.ProjectRepositoryPort;
import com.riwi.assessment.domain.port.out.TaskRepositoryPort;
import com.riwi.assessment.infrastructure.adapter.in.web.dto.request.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepositoryPort taskRepository;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final ProjectRepositoryPort projectRepository;
    private final CurrentUserPort currentUserPort;

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable UUID projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new com.riwi.assessment.domain.exception.BusinessException("Proyecto no encontrado"));

        if (!project.getOwnerId().equals(currentUserPort.getCurrentUserId())) {
            throw new com.riwi.assessment.domain.exception.ForbiddenException("No tienes permiso para ver estas tareas");
        }

        return ResponseEntity.ok(taskRepository.findByProjectId(projectId));
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Task> createTask(
            @PathVariable UUID projectId,
            @RequestBody TaskRequest request) {
        Task task = Task.builder()
                .projectId(projectId)
                .title(request.getTitle())
                .completed(false)
                .build();
        return ResponseEntity.ok(taskRepository.save(task));
    }

    @PatchMapping("/tasks/{id}/complete")
    public ResponseEntity<?> completeTask(@PathVariable UUID id) {
        completeTaskUseCase.complete(id);
        return ResponseEntity.ok(Map.of("message", "¡Tarea completada correctamente!"));
    }
}