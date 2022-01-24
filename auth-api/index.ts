import 'reflect-metadata';
import express from "express";
import router from './src/modules/user/routes/UserRoutes';

import './src/modules/db/dbConnect';
import authMiddleware from './src/modules/middlewares/authMiddleware';

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

app.use(express.json());
app.use(router);

app.listen(PORT, () => console.log(`Running at http://localhost:${PORT}`));