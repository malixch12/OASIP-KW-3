import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  base: '/kw3',
  plugins: [vue()],
  server:{
    proxy:{
      '/api': {
        target: "https://localhost:8080",
        changeOrigin: true
      } 
    }
  }
})
