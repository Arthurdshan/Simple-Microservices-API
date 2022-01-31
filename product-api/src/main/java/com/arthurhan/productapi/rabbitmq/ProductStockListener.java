package com.arthurhan.productapi.rabbitmq;

import com.arthurhan.productapi.dtos.ProductStockDTO;
import com.arthurhan.productapi.services.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockListener
{
    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO dto)
    {
        productService.updateProductStock(dto);
    }
}
