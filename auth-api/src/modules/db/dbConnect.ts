import { createConnection } from "typeorm";

createConnection()
.then(() => console.log("connection with database established"))
.catch(() => console.log("db error"));