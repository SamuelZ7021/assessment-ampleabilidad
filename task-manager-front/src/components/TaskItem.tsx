import { CheckCircle2, Circle, Trash2 } from 'lucide-react';
import { Task } from '../api/types';

interface Props {
  task: Task;
  onComplete: (id: string) => void;
}

export const TaskItem = ({ task, onComplete }: Props) => {
  return (
    <div className={`
      group flex items-center justify-between p-4 mb-2 rounded-xl border transition-all duration-300
      ${task.completed 
        ? 'bg-gray-50 border-transparent' 
        : 'bg-white border-gray-100 shadow-sm hover:border-indigo-200 hover:shadow-md'}
    `}>
      <div className="flex items-center gap-3">
        <button 
          onClick={() => !task.completed && onComplete(task.id)}
          className={`transition-colors ${task.completed ? 'cursor-default' : 'hover:text-indigo-600'}`}
        >
          {task.completed ? (
            <CheckCircle2 className="text-green-500" size={22} />
          ) : (
            <Circle className="text-gray-300 group-hover:text-indigo-400" size={22} />
          )}
        </button>
        
        <span className={`text-sm font-medium transition-all duration-500 ${
          task.completed ? 'line-through text-gray-400' : 'text-gray-700'
        }`}>
          {task.title}
        </span>
      </div>

      {/* Botón de eliminar (opcional, oculto por defecto y aparece al hacer hover) */}
      <button className="opacity-0 group-hover:opacity-100 p-2 text-gray-400 hover:text-red-500 transition-opacity">
        <Trash2 size={16} />
      </button>
    </div>
  );
};