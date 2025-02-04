import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
        '/course': {
            target: 'http://localhost:9999', // 后端地址
            changeOrigin: true,
        },
    },
},
})
