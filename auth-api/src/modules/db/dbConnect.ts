import { createConnection } from "typeorm";
import { DB_HOST, DB_NAME, DB_PASSWORD, DB_PORT, DB_USER } from "../constants/secrets";
import { User } from "../user/model/User.entity";

createConnection({
    type: "postgres",
    host: DB_HOST,
    port: Number(DB_PORT),
    username: DB_USER,
    password: DB_PASSWORD,
    database: DB_NAME,
    synchronize: true,
    entities: [
        User
    ]
})
.then(() => console.log("connection with database established"))
.catch((e) => console.log(e));