FROM node

WORKDIR "D:\Programming\BaOSS\front-end-service"

COPY package*.json ./

RUN npm install -g @angular/cli @angular-devkit/build-angular && npm install

EXPOSE 4200

CMD ["npm", "start"]

