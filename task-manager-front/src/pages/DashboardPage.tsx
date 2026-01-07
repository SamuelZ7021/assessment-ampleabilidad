import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useProjects } from '../hooks/useProjects';
import { PlusCircle, Power, FolderOpen, CheckCircle } from 'lucide-react';
import { ProjectCard } from '../components/ProjectCard';

export const DashboardPage = () => {
  const { logout } = useAuth();
  const { projects, loading, createProject, activateProject } = useProjects();
  const [newProjectName, setNewProjectName] = useState('');

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newProjectName.trim()) return;
    await createProject(newProjectName);
    setNewProjectName('');
  };

  return (
    <div className="min-h-screen p-8 max-w-4xl mx-auto">
      {/* Header */}
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-indigo-600">Mis Proyectos</h1>
        <button 
          onClick={logout}
          className="flex items-center gap-2 text-red-500 hover:bg-red-50 px-4 py-2 rounded-lg transition"
        >
          <Power size={18} /> Salir
        </button>
      </div>

      {/* Formulario Crear */}
      <form onSubmit={handleCreate} className="mb-8 flex gap-2">
        <input 
          type="text" 
          placeholder="Nombre del nuevo proyecto..." 
          className="flex-1 p-3 border rounded-xl shadow-sm focus:ring-2 focus:ring-indigo-400 outline-none"
          value={newProjectName}
          onChange={(e) => setNewProjectName(e.target.value)}
        />
        <button className="bg-indigo-600 text-white px-6 py-3 rounded-xl flex items-center gap-2 hover:bg-indigo-700 transition">
          <PlusCircle size={20} /> Crear
        </button>
      </form>

      {/* Lista de Proyectos */}
      {loading ? (
        <p>Cargando proyectos...</p>
      ) : (
        <div className="grid gap-4">
          {projects.map((project) => (
            <ProjectCard 
            key={project.id} 
            project={project} 
            onActivate={activateProject} 
    />
          ))}
          {projects.length === 0 && <p className="text-center text-gray-500 py-10">No tienes proyectos aún.</p>}
        </div>
      )}
    </div>
  );
};