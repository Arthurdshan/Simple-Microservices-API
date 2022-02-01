import { Channel } from 'amqplib';
import amqp from 'amqplib/callback_api';
import { RABBIT_MQ_URL } from '../constants/secrets';
import { PRODUCT_STOCK_UPDATE_QUEUE, PRODUCT_STOCK_UPDATE_ROUTING_KEY, PRODUCT_TOPIC, SALES_CONFIRMATION_QUEUE, SALES_CONFIRMATION_ROUTING_KEY } from './queue';

const HALF_SECOND = 500;

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

export async function connectRabbitMq() {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }
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

        setTimeout(() => {
            connection.close();
        }, HALF_SECOND);
    });
}