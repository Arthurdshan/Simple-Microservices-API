package com.arthurhan.productapi.rabbitmq;

import com.arthurhan.productapi.dtos.SalesConfirmationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalesConfirmationSender
{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message)
    {
        try
        {
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
