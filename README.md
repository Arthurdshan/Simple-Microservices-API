# Simple-Microservices-API
Microservices project using rabbitMQ for communication. No API Gateway or service discovery.

## Technologies
- Java 17
- Spring Boot
- Spring Cloud OpenFeign
- Typescript
- Node.js 14
- Express
- API REST
- PostgreSQL
- TypeORM
- Prisma
- RabbitMQ
- Docker
- Docker-compose
- JWT
- Axios

## Microservices
- Authorization
- Product
- Sales

## Docker commands
Container Auth-DB:
```
docker run --name auth-db -p 5432:5432 -e POSTGRES_DB=auth-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11
```

Container Product-DB:
```
docker run --name product-db -p 5433:5432 -e POSTGRES_DB=product-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11
```

Container Sales-DB:
```
docker run --name sales-db -p 5434:5432 -e POSTGRES_DB=sales-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11
```

Container RabbitMQ:
```
docker run --name sales_rabbit -p 5672:5672 -p 25676:25676 -p 15672:15672 rabbitmq:3-management
```

Docker compose:
```
docker-compose up --build
```