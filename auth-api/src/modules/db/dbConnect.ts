import { createConnection } from "typeorm";

createConnection().then(() => console.log("connection with database established"));