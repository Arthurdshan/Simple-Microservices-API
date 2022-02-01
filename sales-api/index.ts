import express from 'express';
import { testData } from './src/config/testData';
import authMiddleware from './src/middlewares/authMiddleware';
import { connectRabbitMq } from './src/rabbitmq/rabbitConfig';

const app = express();
const env = process.env;
const port = env.PORT || 8082;

// testData();

connectRabbitMq();

app.use(authMiddleware);

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: "Sales-API",
        status: "up"
    })
});

app.listen(port, () => {
    console.log(`Server running at ${port}`);
});
