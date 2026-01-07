import axios, { InternalAxiosRequestConfig } from 'axios';

// Definimos la URL base de tu backend (puerto 8081 según configuramos antes)
const api = axios.create({
    baseURL: 'http://localhost:8081/api',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Interceptor para añadir el token JWT a cada petición protegida
api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const token = localStorage.getItem('access_token');
    
    if (token && config.headers) {
        // Agregamos el header Authorization: Bearer <token>
        config.headers.Authorization = `Bearer ${token}`;
    }
    
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Interceptor para manejar errores globales (ej: token expirado)
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) { // SOLO 401 dispara el logout
            localStorage.removeItem('access_token');
            window.location.href = '/login';
        }
        // El 403 pasará al bloque catch del componente para mostrar el Toast
        return Promise.reject(error);
    }
)

export default api;