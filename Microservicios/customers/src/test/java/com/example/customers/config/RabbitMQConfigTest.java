package com.example.customers.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

class RabbitMQConfigTest {

    private final RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();

    @Test
    void testCustomerRequestQueue() {
        // Act
        Queue queue = rabbitMQConfig.customerRequestQueue();

        // Assert
        assertNotNull(queue, "The queue should not be null");
        assertEquals("customer.request.queue", queue.getName());
        assertTrue(queue.isDurable(), "The queue should be durable");
    }

    @Test
    void testCustomerResponseQueue() {
        // Act
        Queue queue = rabbitMQConfig.customerResponseQueue();

        // Assert
        assertNotNull(queue, "The queue should not be null");
        assertEquals("customer.response.queue", queue.getName());
        assertTrue(queue.isDurable(), "The queue should be durable");
    }

    @Test
    void testJsonMessageConverter() {
        // Act
        MessageConverter converter = rabbitMQConfig.jsonMessageConverter();

        // Assert
        assertNotNull(converter, "The message converter should not be null");
        assertTrue(converter instanceof org.springframework.amqp.support.converter.Jackson2JsonMessageConverter,
                "The converter should be an instance of Jackson2JsonMessageConverter");
    }

    @Test
    void testRabbitTemplate() {
        // Arrange
        ConnectionFactory mockConnectionFactory = mock(ConnectionFactory.class);
        MessageConverter mockConverter = mock(MessageConverter.class);

        RabbitMQConfig config = new RabbitMQConfig() {
            @Override
            public MessageConverter jsonMessageConverter() {
                return mockConverter;
            }
        };

        // Act
        RabbitTemplate rabbitTemplate = config.rabbitTemplate(mockConnectionFactory);

        // Assert
        assertNotNull(rabbitTemplate, "RabbitTemplate should not be null");
        assertEquals(mockConnectionFactory, rabbitTemplate.getConnectionFactory(),
                "The connection factory should match the mock");
        assertEquals(mockConverter, rabbitTemplate.getMessageConverter(),
                "The message converter should match the mock converter");
    }
}
