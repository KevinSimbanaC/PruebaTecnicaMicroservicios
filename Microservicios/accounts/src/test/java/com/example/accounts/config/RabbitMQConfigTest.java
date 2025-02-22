package com.example.accounts.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Mock
    private ConnectionFactory connectionFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCustomerRequestQueue() {
        Queue queue = rabbitMQConfig.customerRequestQueue();
        assertNotNull(queue);
        assertEquals("customer.request.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void testCustomerResponseQueue() {
        Queue queue = rabbitMQConfig.customerResponseQueue();
        assertNotNull(queue);
        assertEquals("customer.response.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void testJsonMessageConverter() {
        MessageConverter converter = rabbitMQConfig.jsonMessageConverter();
        assertNotNull(converter);
        assertInstanceOf(Jackson2JsonMessageConverter.class, converter);
    }

    @Test
    void testRabbitTemplate() {
        RabbitTemplate rabbitTemplate = rabbitMQConfig.rabbitTemplate(connectionFactory);
        assertNotNull(rabbitTemplate);
        assertInstanceOf(Jackson2JsonMessageConverter.class, rabbitTemplate.getMessageConverter());
    }
}
