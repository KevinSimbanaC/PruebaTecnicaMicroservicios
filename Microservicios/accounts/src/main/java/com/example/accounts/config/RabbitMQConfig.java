package com.example.accounts.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Cola para enviar solicitudes relacionadas con clientes.
     */
    @Bean
    public Queue customerRequestQueue() {
        return new Queue("customer.request.queue", true); // Duradera
    }

    /**
     * Cola para recibir respuestas relacionadas con clientes.
     */
    @Bean
    public Queue customerResponseQueue() {
        return new Queue("customer.response.queue", true); // Duradera
    }

    // Configuración del convertidor JSON
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Configuración del RabbitTemplate para usar el convertidor JSON
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
