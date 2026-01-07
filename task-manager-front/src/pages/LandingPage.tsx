import { useNavigate } from 'react-router-dom';
import { CheckCircle, Zap, Shield, ArrowRight } from 'lucide-react';

export const LandingPage = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-white">
      {/* Navegación simple */}
      <nav className="flex justify-between items-center p-6 max-w-7xl mx-auto">
        <div className="flex items-center gap-2">
          <div className="bg-indigo-600 p-2 rounded-lg">
            <CheckCircle className="text-white" size={24} />
          </div>
          <span className="text-xl font-bold tracking-tight">TaskMaster</span>
        </div>
        <button 
          onClick={() => navigate('/login')}
          className="font-semibold text-gray-600 hover:text-indigo-600 transition"
        >
          Iniciar Sesión
        </button>
      </nav>

      {/* Hero Section */}
      <header className="py-20 px-6 text-center max-w-4xl mx-auto">
        <h1 className="text-5xl md:text-6xl font-extrabold text-gray-900 mb-6 tracking-tight">
          Gestiona tus proyectos con <span className="text-indigo-600">precisión.</span>
        </h1>
        <p className="text-lg text-gray-600 mb-10 leading-relaxed">
          La herramienta definitiva para organizar tareas, activar proyectos y mantener a tu equipo sincronizado. Simple, potente y seguro.
        </p>
        <div className="flex flex-col sm:flex-row gap-4 justify-center">
          <button 
            onClick={() => navigate('/register')}
            className="bg-indigo-600 text-white px-8 py-4 rounded-2xl font-bold text-lg flex items-center justify-center gap-2 hover:bg-indigo-700 shadow-xl shadow-indigo-100 transition-all hover:-translate-y-1"
          >
            Empezar Gratis <ArrowRight size={20} />
          </button>
        </div>
      </header>

      {/* Características */}
      <section className="bg-gray-50 py-20 px-6">
        <div className="max-w-7xl mx-auto grid md:grid-cols-3 gap-12">
          <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
            <Zap className="text-amber-500 mb-4" size={32} />
            <h3 className="text-xl font-bold mb-2">Rápido y Fluido</h3>
            <p className="text-gray-600">Interfaz optimizada para que nada te detenga mientras creas tus tareas.</p>
          </div>
          <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
            <Shield className="text-blue-500 mb-4" size={32} />
            <h3 className="text-xl font-bold mb-2">Seguridad JWT</h3>
            <p className="text-gray-600">Tus datos están protegidos con los estándares más altos de encriptación.</p>
          </div>
          <div className="bg-white p-8 rounded-3xl shadow-sm border border-gray-100">
            <CheckCircle className="text-green-500 mb-4" size={32} />
            <h3 className="text-xl font-bold mb-2">Arquitectura Hexagonal</h3>
            <p className="text-gray-600">Backend robusto construido con las mejores prácticas de ingeniería.</p>
          </div>
        </div>
      </section>
    </div>
  );
};