import amqp from 'amqplib/callback_api';
import { RABBIT_MQ_URL } from '../../constants/secrets';
import { SALES_CONFIRMATION_QUEUE } from '../../rabbitmq/queue';

export function listenToConfirmationQueue() {
    amqp.connect(RABBIT_MQ_URL, (error, connection) => {
        if (error) {
            throw error;
        }

        console.info("Listening to sales confirmation queue...");

        connection.createChannel((error, channel) => {
            if (error) {
                throw error;
            }

            channel.consume(SALES_CONFIRMATION_QUEUE, (message) => {
                console.info(message?.content.toString());
            }, { noAck: true });
        });
    });
}