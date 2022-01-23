import { createConnection } from "typeorm";
import { User } from "../../modules/user/model/User";

const connection = createConnection({
    type: "postgres",
    host: "localhost",
    port: 5432,
    username: "postgres",
    password: "123456",
    database: "auth-db",
    synchronize: true,
    entities: [
        User
    ]
});

export default connection;