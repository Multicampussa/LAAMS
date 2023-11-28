#  Dockerfile
FROM node:18 as builder

WORKDIR /app

COPY ./package*.json ./

RUN npm install --force

COPY . .

RUN npm run build

FROM nginx:latest


COPY ./Nginx.conf /etc/nginx/conf.d/react.conf

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]
