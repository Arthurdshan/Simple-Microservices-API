FROM node:14
WORKDIR .
COPY package.json ./
RUN yarn
COPY . .

CMD ["npx", "ts-node", "app.ts"]

EXPOSE 8080