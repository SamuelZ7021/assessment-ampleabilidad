import React, { useState, useEffect } from 'react';
import { Project, Task as TaskType } from '../api/types';
import { useTasks } from '../hooks/useTasks';
import { Plus, ChevronDown, ChevronUp, Layout, CheckCircle, ListTodo } from 'lucide-react';
import { TaskItem } from './TaskItem';

interface Props {
  project: Project;
  onActivate: (id: string) => void;
}

export const ProjectCard = ({ project, onActivate }: Props) => {
  const [showTasks, setShowTasks] = useState(false);
  const [taskTitle, setTaskTitle] = useState('');
  const { tasks, fetchTasks, createTask, completeTask, loadingTasks } = useTasks();

  // 1. Cargar tareas automáticamente cuando se expande el acordeón
  useEffect(() => {
    if (showTasks) {
      fetchTasks(project.id);
    }
  }, [showTasks, project.id]);

  // 2. Cálculos para la barra de progreso
  const completedTasks = tasks.filter(t => t.completed).length;
  const progressPercentage = tasks.length > 0 
    ? Math.round((completedTasks / tasks.length) * 100) 
    : 0;

  const handleAddTask = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!taskTitle.trim()) return;
    try {
      await createTask(project.id, taskTitle);
      setTaskTitle('');
    } catch (err) {
      // El error ya es manejado por el toast en el hook
    }
  };

  return (
    <div className={`
      bg-white rounded-3xl border transition-all duration-300 
      ${showTasks ? 'ring-2 ring-indigo-100 shadow-xl' : 'border-gray-100 shadow-sm hover:shadow-md'}
    `}>
      {/* --- CABECERA DEL PROYECTO --- */}
      <div 
        className="p-6 flex flex-col md:flex-row justify-between items-start md:items-center gap-4 cursor-pointer"
        onClick={() => setShowTasks(!showTasks)}
      >
        <div className="flex items-center gap-4 w-full md:w-auto">
          <div className={`p-3 rounded-2xl ${project.status === 'ACTIVE' ? 'bg-green-50' : 'bg-indigo-50'}`}>
            <Layout className={project.status === 'ACTIVE' ? 'text-green-600' : 'text-indigo-600'} size={24} />
          </div>
          <div className="flex-1">
            <h3 className="text-lg font-bold text-gray-800 leading-tight">{project.name}</h3>
            <div className="flex items-center gap-2 mt-1">
              <span className={`text-[10px] uppercase tracking-wider px-2 py-0.5 rounded-full font-bold ${
                project.status === 'ACTIVE' ? 'bg-green-100 text-green-700' : 'bg-amber-100 text-amber-700'
              }`}>
                {project.status}
              </span>
              <span className="text-xs text-gray-400 flex items-center gap-1">
                <ListTodo size={12} /> {tasks.length} tareas
              </span>
            </div>
          </div>
        </div>

        {/* Barra de Progreso Visual */}
        <div className="w-full md:w-48 space-y-1">
          <div className="flex justify-between text-[10px] font-bold text-gray-500 uppercase tracking-tighter">
            <span>Progreso</span>
            <span>{progressPercentage}%</span>
          </div>
          <div className="h-2 w-full bg-gray-100 rounded-full overflow-hidden">
            <div 
              className={`h-full transition-all duration-1000 ease-out ${
                progressPercentage === 100 ? 'bg-green-500' : 'bg-indigo-500'
              }`}
              style={{ width: `${progressPercentage}%` }}
            />
          </div>
        </div>
        
        <div className="flex items-center gap-4 ml-auto md:ml-0">
          {project.status === 'DRAFT' && (
            <button 
              onClick={(e) => { e.stopPropagation(); onActivate(project.id); }}
              className="text-xs font-bold bg-indigo-600 text-white px-4 py-2 rounded-xl hover:bg-indigo-700 shadow-lg shadow-indigo-100 transition active:scale-95"
            >
              Activar
            </button>
          )}
          <div className={`p-2 rounded-xl transition-colors ${showTasks ? 'bg-indigo-50 text-indigo-600' : 'bg-gray-50 text-gray-400'}`}>
            {showTasks ? <ChevronUp size={20}/> : <ChevronDown size={20}/>}
          </div>
        </div>
      </div>

      {/* --- CUERPO (LISTA DE TAREAS) --- */}
      <div className={`
        overflow-hidden transition-all duration-500 ease-in-out
        ${showTasks ? 'max-h-[1000px] opacity-100' : 'max-h-0 opacity-0 invisible'}
      `}>
        <div className="p-6 pt-0 bg-gray-50/30 border-t border-gray-50">
          
          {/* Formulario para nueva tarea */}
          <form onSubmit={handleAddTask} className="flex gap-2 my-6">
            <div className="relative flex-1 group">
              <input 
                value={taskTitle}
                onChange={(e) => setTaskTitle(e.target.value)}
                placeholder="¿Qué tarea tienes pendiente?" 
                className="w-full text-sm p-3 pl-4 rounded-2xl border-2 border-white bg-white shadow-sm focus:border-indigo-400 focus:ring-0 outline-none transition"
              />
            </div>
            <button 
              disabled={!taskTitle.trim()}
              className="p-3 bg-indigo-600 text-white rounded-2xl hover:bg-indigo-700 shadow-lg shadow-indigo-100 active:scale-95 transition disabled:opacity-50 disabled:scale-100"
            >
              <Plus size={20} />
            </button>
          </form>

          {/* Listado de items */}
          <div className="space-y-1">
            {loadingTasks ? (
              <div className="flex flex-col items-center py-10 text-gray-400 animate-pulse">
                <ListTodo size={32} className="mb-2" />
                <p className="text-xs font-medium">Sincronizando tareas...</p>
              </div>
            ) : tasks.length > 0 ? (
              tasks.map(task => (
                <TaskItem 
                  key={task.id} 
                  task={task} 
                  onComplete={() => completeTask(task.id)} 
                />
              ))
            ) : (
              <div className="text-center py-12 bg-white/50 rounded-3xl border-2 border-dashed border-gray-100">
                <p className="text-sm text-gray-400 font-medium">Tu lista está vacía. ¡Empieza añadiendo una!</p>
              </div>
            )}
          </div>

          {/* Footer del acordeón */}
          {tasks.length > 0 && (
            <div className="mt-4 pt-4 border-t border-gray-100 flex justify-center">
              <p className="text-[10px] text-gray-400 font-bold uppercase tracking-widest">
                {completedTasks} de {tasks.length} tareas finalizadas
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};