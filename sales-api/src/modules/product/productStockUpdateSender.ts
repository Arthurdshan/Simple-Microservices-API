import amqp from 'amqplib/callback_api';
import { RABBIT_MQ_URL } from '../../constants/secrets';
import { PRODUCT_TOPIC, PRODUCT_STOCK_UPDATE_ROUTING_KEY } from '../../rabbitmq/queue';

export function sendProductStockUpdateQueue(message: object) {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }

        connection.createChannel((error, channel) => {
            if (error) {
                throw error;
            }
            let jsonStringMessage = JSON.stringify(message);
            console.info("Sending message: " + jsonStringMessage);
            channel.publish(PRODUCT_TOPIC, PRODUCT_STOCK_UPDATE_ROUTING_KEY, Buffer.from(jsonStringMessage));
            console.info("Message sent successfuly!");
        });
    });
}