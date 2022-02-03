FROM node:14
WORKDIR .
COPY package.json ./
COPY prisma ./prisma/

RUN yarn

COPY . .

CMD ["yarn", "prod"]

EXPOSE 8082