package com.riwi.assessment.application.usecase;

import com.riwi.assessment.domain.exception.BusinessException;
import com.riwi.assessment.domain.exception.ForbiddenException;
import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.model.Task;
import com.riwi.assessment.domain.port.in.CompleteTaskUseCase;
import com.riwi.assessment.domain.port.out.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class TaskService implements CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepository;
    private final ProjectRepositoryPort projectRepository;
    private final AuditLogPort auditLog;
    private final NotificationPort notification;
    private final CurrentUserPort currentUser;

    @Override
    public void complete(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException("Tarea no encontrada"));

        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new ForbiddenException("Acceso denegado: No eres el propietario de este proyecto");
        }

        if (task.isCompleted()) {
            throw new BusinessException("La tarea ya está completada");
        }

        task.setCompleted(true);
        taskRepository.save(task);

        auditLog.register("TASK_COMPLETED", taskId);
        notification.notify("Tarea completada: " + task.getTitle());
    }
}