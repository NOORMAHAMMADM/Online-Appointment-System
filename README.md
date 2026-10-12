# ONLINE APPOINTMENT SYSTEM

A full-stack appointment scheduling application built with React JS on the frontend and Spring Boot with Spring Data JPA on the backend.

## Technology

- React JS
- Spring Boot
- Java
- Spring Data JPA
- MySQL
- REST API

## Main Features

- User registration/login
- Appointment categories
- Provider selection
- Provider schedules
- Appointment booking
- Cancel appointment
- Reschedule appointment
- Provider dashboard
- Admin dashboard
- Appointment management
- Demo AI Assistant
- Free demo notification

## Project Structure

```text
Online-Appointment-System/
├── backend/          # Spring Boot application (Java, REST API, JPA)
├── frontend/         # React application (Vite, Tailwind/CSS, pages & components)
└── README.md         # Project documentation and setup guide
```

## Architecture

React JS
↓
REST API
↓
Spring Boot
↓
Spring Data JPA
↓
MySQL

## Local Development

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Run the combined application

1. From the project root, install frontend dependencies once:

```bash
cd frontend && npm ci
```

2. Navigate to the backend directory and start Spring Boot:

```bash
cd ../backend
./mvnw spring-boot:run
```

The Maven build runs the React production build and places its output under generated `static/app` resources. Spring Boot serves the React application at `http://localhost:8080`; the original static HTML files remain in `src/main/resources/static` and are not overwritten. React routes are forwarded to the app entry point so they can be refreshed directly.

During development, run Spring Boot on port 8080 and Vite on port 5173. Vite proxies `/api` requests to Spring Boot. For deployments where the frontend and API use different origins, set `VITE_API_BASE_URL` before building; leave it blank when Spring Boot serves both on the same origin.

## Environment Configuration

The frontend uses the `VITE_API_BASE_URL` environment variable. A sample file is included at `frontend/.env.example`.

The backend uses environment-based configuration values such as:

- `PORT`: Server port (defaults to 8080, automatically set by Render)
- `DB_URL`: MySQL connection URL (e.g., `jdbc:mysql://host:port/database`)
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `APP_CORS_ALLOWED_ORIGINS`: Allowed origins (e.g., `https://your-app.vercel.app`)
- `JPA_DDL_AUTO`: Hibernate DDL auto strategy (default: `update`)

Do not commit real secrets or live credentials to source control.

## Deployment Guide

### 1. Backend on Render (Docker)

1. Create a new **Web Service** on [Render](https://render.com/).
2. Connect your Git repository.
3. Configure the service:
   - **Environment**: Docker
   - **Root Directory**: `backend` (or leave empty to use root Dockerfile)
   - **Dockerfile Path**: `./backend/Dockerfile` (or `Dockerfile` if Root Directory is `backend`)
   - Alternatively, Render will automatically detect the provided [render.yaml](file:///e:/noor-tt/Online-Appointment-System/render.yaml) blueprint!
4. Add environment variables in Render:
   - `DB_URL`: `jdbc:mysql://<your-db-host>:<port>/<db-name>`
   - `DB_USERNAME`: `<your-db-username>`
   - `DB_PASSWORD`: `<your-db-password>`
   - `APP_CORS_ALLOWED_ORIGINS`: `https://<your-frontend-app>.vercel.app`
5. Click **Deploy Web Service**. Render exposes your backend at `https://<service-name>.onrender.com`.

### 2. Frontend on Vercel

1. Import your Git repository into [Vercel](https://vercel.com/).
2. Select **Root Directory**: `frontend` (or keep root; both [frontend/vercel.json](file:///e:/noor-tt/Online-Appointment-System/frontend/vercel.json) and root [vercel.json](file:///e:/noor-tt/Online-Appointment-System/vercel.json) are configured).
3. Framework Preset: **Vite**
4. Add Environment Variables in Vercel:
   - `VITE_API_BASE_URL`: `https://<your-render-backend-service>.onrender.com`
5. Deploy! Vercel handles SPA rewrites, assets, and includes `@vercel/analytics` and `@vercel/speed-insights`.

## Author

- **NOORMAHAMMADM** ([@NOORMAHAMMADM](https://github.com/NOORMAHAMMADM))

