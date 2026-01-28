# 阶段 1: 构建编译
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 阶段 2: Nginx 托管
FROM nginx:stable-alpine
# 复制编译后的产物
COPY --from=build /app/dist /usr/share/nginx/html
# 复制刚才写的 nginx 配置
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]