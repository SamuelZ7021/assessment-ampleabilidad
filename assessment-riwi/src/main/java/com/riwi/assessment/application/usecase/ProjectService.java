package com.riwi.assessment.application.usecase;

import com.riwi.assessment.domain.exception.BusinessException;
import com.riwi.assessment.domain.exception.ForbiddenException;
import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.port.in.ActivateProjectUseCase;
import com.riwi.assessment.domain.port.out.*;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepository;
    private final TaskRepositoryPort taskRepository;
    private final AuditLogPort auditLog;
    private final NotificationPort notification;
    private final CurrentUserPort currentUser;

    @Override
    public void activate(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BusinessException("Proyecto no encontrado"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new ForbiddenException("Acceso denegado: No eres el propietario de este proyecto");
        }

        long activeTasks = taskRepository.countActiveTasksByProjectId(projectId);
        if (activeTasks == 0) {
            throw new BusinessException("Debe tener al menos una tarea activa para activarse");
        }

        project.setStatus(Project.ProjectStatus.ACTIVE);
        projectRepository.save(project);

        auditLog.register("PROJECT_ACTIVATED", projectId);
        notification.notify("El proyecto " + project.getName() + " ha sido activado");
    }
}