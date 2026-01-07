package com.riwi.assessment.infrastructure.adapter.out.persistence.entity;

import com.riwi.assessment.domain.model.Project.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    private boolean deleted = false;
}