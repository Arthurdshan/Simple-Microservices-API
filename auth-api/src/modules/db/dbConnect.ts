import { createConnection } from "typeorm";

createConnection({
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "admin",
    password: "123456",
    database: "auth-db",
    synchronize: true,
    entities: [
        "../../**/**.entity{.ts, .js}"
    ]
})
.then(() => console.log("connection with database established"))
.catch((e) => console.log(e));