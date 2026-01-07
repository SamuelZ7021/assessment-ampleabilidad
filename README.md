
---

# 🚀 TaskMaster: Sistema de Gestión de Proyectos y Tareas

**TaskMaster** es una plataforma integral diseñada para la organización eficiente de flujos de trabajo.
El sistema permite a los usuarios gestionar el ciclo de vida de sus proyectos, desde la creación como borrador hasta su activación mediante el cumplimiento de reglas de negocio específicas.

---

## 🛠️ Tecnologías Utilizadas

### Backend (Arquitectura Hexagonal)

* **Java 17 + Spring Boot 4.0.1**: Motor principal del servidor.
* **Spring Security + JWT**: Autenticación stateless para máxima seguridad.
* **PostgreSQL**: Base de datos relacional para persistencia de datos.
* **Hibernate / JPA**: Mapeo de entidades y gestión ORM.
* **Swagger / OpenAPI 3**: Documentación interactiva de la API.

### Frontend (Interfaz Moderna)

* **React + TypeScript**: Interfaz con tipado fuerte.
* **Tailwind CSS v4**: Sistema de estilos moderno y altamente optimizado.
* **Vite**: Compilador y servidor de desarrollo rápido.
* **Axios**: Cliente HTTP con interceptores automáticos para JWT.
* **Sonner & Lucide React**: Notificaciones interactivas e iconografía profesional.

---

## 📋 Requisitos Previos

Para ejecutar el sistema completo necesitas:

* **Java JDK 17** o superior.
* **Node.js v18+** y **npm**.
* **PostgreSQL** corriendo en el puerto `5434` (o ajustar configuración).
* Base de datos creada con el nombre:

  ```sql
  task_manager_db
  ```

---

## 🚀 Instalación y Ejecución

### 1️⃣ Servidor (Backend)

1. Navega a la carpeta raíz del proyecto Java.
2. Verifica las credenciales de la base de datos en:

   ```
   src/main/resources/application.properties
   ```
3. Ejecuta el servidor:

   ```bash
   ./mvnw spring-boot:run
   ```

📡 La API estará disponible en:
**[http://localhost:8081/api](http://localhost:8081/api)**

---

### 2️⃣ Cliente (Frontend)

1. Navega a la carpeta del frontend:

   ```
   task-manager-front
   ```

2. Instala las dependencias:

   ```bash
   npm install
   ```

3. Inicia el servidor de desarrollo:

   ```bash
   npm run dev
   ```

🌐 Abre tu navegador en:
**[http://localhost:5173](http://localhost:5173)**

---

## 🏗️ Estructura del Proyecto

### Backend

* **domain/**: Entidades de negocio y puertos (interfaces).
* **application/**: Casos de uso y lógica de validación.
* **infrastructure/**: Adaptadores (Controladores REST, Repositorios JPA, Seguridad).

### Frontend

* **api/**: Configuración de Axios y tipos de datos.
* **components/**: Componentes reutilizables (`ProjectCard`, `TaskItem`).
* **hooks/**: Lógica de sincronización con la API (`useProjects`, `useTasks`).
* **pages/**: Vistas principales (`Landing`, `Login`, `Dashboard`).

---

## 📖 Guía de Uso del Sistema

### Flujo de Proyectos y Tareas

1. **Registro y Acceso**
   Crea una cuenta e inicia sesión para obtener tu token JWT.

2. **Crear Proyecto**
   Desde el Dashboard, crea un proyecto.
   El estado inicial será **DRAFT (Borrador)**.

3. **Gestionar Tareas**
   Añade tareas dentro del proyecto.

4. **Activación**
   El botón **"Activar"** solo estará habilitado si el proyecto tiene al menos una tarea activa.
   Si no se cumple, el sistema lanza una excepción controlada.

5. **Completar Tareas**
   Marca tareas como finalizadas y observa el progreso en la barra visual.

---

## 🔍 Documentación de la API (Swagger)

Accede a la documentación interactiva en:
👉 **[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)**

### Pasos para probar en Swagger

1. Ejecuta:

   ```
   POST /api/auth/register
   ```
2. Ejecuta:

   ```
   POST /api/auth/login
   ```

   Copia el `access_token`.
3. Pulsa **Authorize** y pega el token.
4. Prueba los endpoints de **Proyectos** y **Tareas**.

---

## 🧪 Pruebas Unitarias

El backend cuenta con pruebas para los casos de uso críticos.

Ejecutar pruebas:

```bash
./mvnw test
```

✔️ Cobertura incluida:

* Activación de proyectos.
* Control de propiedad de recursos.
* Reglas de completado de tareas.

---

## 💡 Decisiones Técnicas Clave

* **Arquitectura Hexagonal**
  Aísla la lógica de negocio del framework, base de datos y cliente web.

* **Manejo Global de Excepciones**
  `GlobalExceptionHandler` transforma errores técnicos y de negocio en respuestas claras para el frontend.

* **Seguridad JWT**
  Autenticación stateless sin sesiones de servidor.

* **Tailwind CSS v4**
  Rendimiento optimizado y diseño altamente componible.

---

## 🔑 Credenciales de Prueba (Swagger)

Utiliza estas credenciales una vez registrado el usuario:

* **Email:** `samuel2@example.com`
* **Password:** `mi1password2segura`
---# assessment-ampleabilidad
