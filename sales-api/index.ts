import express from 'express';
import authMiddleware from './src/middlewares/authMiddleware';
import { connectRabbitMq } from './src/rabbitmq/rabbitConfig';
import orderRoutes from './src/modules/sales/routes/OrderRoutes';

const app = express();
const env = process.env;
const port = env.PORT || 8082;

connectRabbitMq();

app.use(express.json());
app.use(authMiddleware);
app.use(orderRoutes);

app.listen(port, () => {
    console.log(`Server running at ${port}`);
});
