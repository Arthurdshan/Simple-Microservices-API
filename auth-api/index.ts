import express from "express";
import connection from "./src/config/db/dbConfig";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

connection.then(() => { console.log("connection with database established") });

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: "Auth-API",
        status: "up",
        httpStatus: 200
    })
});

app.listen(PORT, () => {
    console.log(`Running at port ${PORT}`);
});
