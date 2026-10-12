import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig(() => {
  const isBackend = process.env.BUILD_TARGET === 'backend'
  const isVercel = Boolean(process.env.VERCEL)

  const base = process.env.VITE_BASE_PATH || (isBackend ? '/app/' : (isVercel ? '/' : '/app/'))
  const outDir = process.env.VITE_OUT_DIR || (isBackend ? '../backend/target/frontend-dist' : (isVercel ? 'dist' : '../backend/target/frontend-dist'))

  return {
    base,
    plugins: [react()],
    build: {
      outDir,
      emptyOutDir: true,
    },
    server: {
      host: true,
      allowedHosts: true,
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
        },
      },
    },
  }
})
