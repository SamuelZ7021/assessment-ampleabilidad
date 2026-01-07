package com.riwi.assessment.application.usecase;

import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.port.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock private ProjectRepositoryPort projectRepository;
    @Mock private TaskRepositoryPort taskRepository;
    @Mock private AuditLogPort auditLog;
    @Mock private NotificationPort notification;
    @Mock private CurrentUserPort currentUser;

    @InjectMocks private ProjectService projectService;

    private UUID projectId;
    private UUID userId;
    private Project project;

    @BeforeEach
    void setUp() {
        projectId = UUID.randomUUID();
        userId = UUID.randomUUID();
        project = Project.builder()
                .id(projectId)
                .ownerId(userId)
                .status(Project.ProjectStatus.DRAFT)
                .build();
    }

    @Test
    void ActivateProject_WithTasks_ShouldSucceed() { // [cite: 120]
        // Arrange
        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.countActiveTasksByProjectId(projectId)).thenReturn(1L);

        // Act
        projectService.activate(projectId);

        // Assert
        assertEquals(Project.ProjectStatus.ACTIVE, project.getStatus());
        verify(projectRepository).save(project);
        verify(auditLog).register(eq("PROJECT_ACTIVATED"), eq(projectId));
        verify(notification).notify(anyString());
    }

    @Test
    void ActivateProject_WithoutTasks_ShouldFail() { // [cite: 121]
        // Arrange
        when(currentUser.getCurrentUserId()).thenReturn(userId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.countActiveTasksByProjectId(projectId)).thenReturn(0L);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.activate(projectId));
        verify(projectRepository, never()).save(any());
    }

    @Test
    void ActivateProject_ByNonOwner_ShouldFail() { // [cite: 122]
        // Arrange
        UUID strangerId = UUID.randomUUID();
        when(currentUser.getCurrentUserId()).thenReturn(strangerId);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> projectService.activate(projectId));
    }
}