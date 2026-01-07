package com.riwi.assessment.infrastructure.config;

import com.riwi.assessment.application.usecase.ProjectService;
import com.riwi.assessment.application.usecase.TaskService;
import com.riwi.assessment.domain.port.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProjectService projectService(
            ProjectRepositoryPort projectRepo,
            TaskRepositoryPort taskRepo,
            AuditLogPort audit,
            NotificationPort notify,
            CurrentUserPort user) {
        return new ProjectService(projectRepo, taskRepo, audit, notify, user);
    }

    @Bean
    public TaskService taskService(
            TaskRepositoryPort taskRepo,
            ProjectRepositoryPort projectRepo,
            AuditLogPort audit,
            NotificationPort notify,
            CurrentUserPort user) {
        return new TaskService(taskRepo, projectRepo, audit, notify, user);
    }
}