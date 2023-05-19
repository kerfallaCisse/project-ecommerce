### STAGE 1: Build ###
FROM node:latest AS build
WORKDIR /app
COPY package*.json ./
RUN cd /app && npm install
COPY . /app
RUN cd /app && npm run build --prod
### STAGE 2: Run ###
FROM nginx:alpine
RUN ls
COPY /app/nginx.default.conf.template /etc/nginx/templates/default.conf.template
COPY --from=build /app/dist/first_test /usr/share/nginx/html


EXPOSE 80
