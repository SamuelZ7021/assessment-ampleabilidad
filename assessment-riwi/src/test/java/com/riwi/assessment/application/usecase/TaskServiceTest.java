package com.riwi.assessment.application.usecase;

import com.riwi.assessment.domain.model.Project;
import com.riwi.assessment.domain.model.Task;
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
class TaskServiceTest {

    @Mock private TaskRepositoryPort taskRepository;
    @Mock private ProjectRepositoryPort projectRepository;
    @Mock private AuditLogPort auditLog;
    @Mock private NotificationPort notification;
    @Mock private CurrentUserPort currentUser;

    @InjectMocks private TaskService taskService;

    private UUID taskId;
    private UUID projectId;
    private UUID userId;
    private Task task;
    private Project project;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        projectId = UUID.randomUUID();
        userId = UUID.randomUUID();

        task = Task.builder().id(taskId).projectId(projectId).completed(false).build();
        project = Project.builder().id(projectId).ownerId(userId).build();
    }

    @Test
    void CompleteTask_AlreadyCompleted_ShouldFail() { // [cite: 123]
        // Arrange
        task.setCompleted(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(userId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> taskService.complete(taskId));
    }

    @Test
    void CompleteTask_ShouldGenerateAuditAndNotification() { // [cite: 124]
        // Arrange
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(userId);

        // Act
        taskService.complete(taskId);

        // Assert
        assertTrue(task.isCompleted());
        verify(auditLog).register(eq("TASK_COMPLETED"), eq(taskId));
        verify(notification).notify(contains("Tarea completada"));
        verify(taskRepository).save(task);
    }
}