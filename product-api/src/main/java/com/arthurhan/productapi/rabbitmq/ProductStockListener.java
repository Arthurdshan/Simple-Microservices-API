package com.arthurhan.productapi.rabbitmq;

import com.arthurhan.productapi.dtos.ProductStockDTO;
import com.arthurhan.productapi.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductStockListener
{
    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO dto) throws JsonProcessingException
    {
        log.info("Received message with data: {}",
                new ObjectMapper().writeValueAsString(dto));
        productService.updateProductStock(dto);
    }
}
