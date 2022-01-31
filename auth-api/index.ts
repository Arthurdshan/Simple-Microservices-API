import 'reflect-metadata';
import express from "express";
import router from './src/modules/user/routes/UserRoutes';

import './src/modules/db/dbConnect';

const app = express();
const PORT = process.env.PORT || 8080;

app.use(express.json());
app.use(router);

app.listen(PORT, () => console.log(`Running at http://localhost:${PORT}`));