import { Channel } from 'amqplib';
import amqp from 'amqplib/callback_api';
import { RABBIT_MQ_URL } from '../constants/secrets';
import { listenToConfirmationQueue } from '../modules/sales/salesConfirmationListener';
import { PRODUCT_STOCK_UPDATE_QUEUE, PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC, SALES_CONFIRMATION_QUEUE, SALES_CONFIRMATION_ROUTING_KEY } from './queue';

const TWO_SECONDS = 2000;
const HALF_MINUTE = 30000;
const CONTAINER_ENV = "container";

function createQueue(connection: any, queue: string, routingKey: string, topic: string) {
    connection.createChannel((error: any, channel: Channel) => {
        if (error) {
            throw error;
        }

        channel.assertExchange(topic, 'topic', { durable: true });
        channel.assertQueue(queue, { durable: true });
        channel.bindQueue(queue, topic, routingKey);
    });
}

async function loadRabbitMq() {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }

        console.info("Starting rabbitMQ.");

        createQueue(
            connection,
            PRODUCT_STOCK_UPDATE_QUEUE,
            PRODUCT_STOCK_UPDATE_ROUTING_KEY,
            PRODUCT_TOPIC
        );

        createQueue(
            connection,
            SALES_CONFIRMATION_QUEUE,
            SALES_CONFIRMATION_ROUTING_KEY,
            PRODUCT_TOPIC
        );

        console.info("Queues and Topics were defined.");

        setTimeout(() => {
            connection.close();
        }, TWO_SECONDS);
    });

    setTimeout(() => {
        listenToConfirmationQueue();
    }, TWO_SECONDS);
}

export async function connectRabbitMq() {
    const env = process.env.NODE_ENV;
    if (CONTAINER_ENV === env) {
        console.info("waiting for rabbitMQ to start...");
        setInterval(() => {
            loadRabbitMq();
        }, HALF_MINUTE);
    } else {
        loadRabbitMq();
    }
}