#  Dockerfile
FROM node:18 as builder

WORKDIR /app

COPY ./package*.json ./

RUN npm install

COPY . .

RUN npm run build

FROM nginx:latest


COPY ./fullchain.pem /etc/letsencrypt/live/k9d101.p.ssafy.io/fullchain.pem
COPY ./privkey.pem /etc/letsencrypt/live/k9d101.p.ssafy.io/privkey.pem


COPY ./Nginx.conf /etc/nginx/conf.d/react.conf

COPY --from=builder /app/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]