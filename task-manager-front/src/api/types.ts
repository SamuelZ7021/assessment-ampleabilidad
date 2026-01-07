// Basado en UserEntity y AuthController
export interface AuthResponse {
  access_token: string;
}

export interface MessageResponse {
  message: string;
}

// Basado en ProjectEntity
export enum ProjectStatus {
  DRAFT = 'DRAFT',
  ACTIVE = 'ACTIVE'
}

export interface Project {
  id: string; // UUID en Java
  name: string;
  ownerId: string;
  status: ProjectStatus;
  deleted: boolean;
}

// Basado en TaskEntity
export interface Task {
  id: string;
  projectId: string;
  title: string;
  completed: boolean;
  deleted: boolean;
}