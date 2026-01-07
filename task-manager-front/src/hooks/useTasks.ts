import { useState } from 'react';
import api from '../api/axios';
import { Task } from '../api/types';
import { toast } from 'sonner';

export const useTasks = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loadingTasks, setLoadingTasks] = useState(false);

  const fetchTasks = async (projectId: string) => {
    setLoadingTasks(true);
    try {
      const response = await api.get<Task[]>(`/projects/${projectId}/tasks`);
      setTasks(response.data);
    } catch (error) {
      toast.error("Error al cargar las tareas");
    } finally {
      setLoadingTasks(false);
    }
  };

  const createTask = async (projectId: string, title: string) => {
    try {
      const response = await api.post<Task>(`/projects/${projectId}/tasks`, { title });
      setTasks(prev => [response.data, ...prev]); // Actualizamos el estado global del hook
      return response.data;
    } catch (error) {
      toast.error("No se pudo crear la tarea");
      throw error;
    }
  };

  const completeTask = async (taskId: string) => {
  try {
    const response = await api.patch(`/tasks/${taskId}/complete`);
    setTasks(prev => prev.map(t => t.id === taskId ? { ...t, completed: true } : t));
    toast.success(response.data.message); 
  } catch (error: any) {
    const message = error.response?.data?.message || "Error al completar tarea";
    toast.error(message);
  }
  };

  return { tasks, loadingTasks, fetchTasks, createTask, completeTask };
};