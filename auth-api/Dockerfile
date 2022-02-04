FROM node:14
WORKDIR .
COPY package.json ./
RUN yarn
COPY . .

CMD ["yarn", "prod"]

EXPOSE 8080