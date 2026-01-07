import { useState, useEffect } from 'react';
import api from '../api/axios';
import { Project } from '../api/types';
import { toast } from 'sonner';

export const useProjects = () => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchProjects = async () => {
    try {
      const response = await api.get<Project[]>('/projects');
      setProjects(response.data);
    } catch (error) {
      console.error("Error al cargar proyectos", error);
    } finally {
      setLoading(false);
    }
  };

  const createProject = async (name: string) => {
    const response = await api.post<Project>('/projects', { name });
    setProjects([...projects, response.data]);
    return response.data;
  };

  const activateProject = async (id: string) => {
  try {
    const response = await api.patch(`/projects/${id}/activate`);
    toast.success(response.data.message); // Muestra "Proyecto activado con éxito"
    fetchProjects();
  } catch (error: any) {
    const message = error.response?.data?.message || 'Error inesperado';
    toast.error(message);
  }
  };

  useEffect(() => {
    fetchProjects();
  }, []);

  return { projects, loading, createProject, activateProject, refresh: fetchProjects };
};