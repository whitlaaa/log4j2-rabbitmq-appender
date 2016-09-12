package com.whitlaaa.log4j2.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.appender.AbstractManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.util.ShutdownCallbackRegistry;
import org.apache.logging.log4j.message.Message;

public class RabbitMQAppenderManager extends AbstractManager {

    private static final RabbitMQAppenderManagerFactory FACTORY = new RabbitMQAppenderManagerFactory();

    private final AmqpConfig amqpConfig;
    private final ExchangeConfig exchangeConfig;
    private final QueueConfig queueConfig;
    private final MessageConfig messageConfig;

    private Connection connection;
    private Channel channel;

    private RabbitMQAppenderManager(String name, RabbitMQConfiguration config) {
        super(name);

        this.amqpConfig = config.amqpConfig;
        this.exchangeConfig = config.exchangeConfig;
        this.queueConfig = config.queueConfig;
        this.messageConfig = config.messageConfig;

        registerShutdownCallback();
    }

    public static RabbitMQAppenderManager getManager(String name, AmqpConfig amqp, ExchangeConfig exchange, QueueConfig queue, MessageConfig messages) {
        return AbstractManager.getManager(name, FACTORY, new RabbitMQConfiguration(amqp, exchange, queue, messages));
    }

    public void publish(Message message) throws IOException {
        setupConnection();
        setupChannel();

        if (exchangeConfig.isDeclare()) {
            LOGGER.info("Declaring RabbitMQ exchange: " + exchangeConfig.getName());
            channel.exchangeDeclare(exchangeConfig.getName(), exchangeConfig.getType().name(), exchangeConfig.isDurable());
        }

        if (queueConfig.isDeclare()) {
            LOGGER.info("Declaring RabbitMQ queue: " + queueConfig.getName());
            channel.queueDeclare(queueConfig.getName(), queueConfig.isDurable(), queueConfig.isExclusive(), queueConfig.isAutoDelete(), null);
            channel.queueBind(queueConfig.getName(), exchangeConfig.getName(), queueConfig.getRoutingKey());
        }

        LOGGER.info("Publishing RabbitMQ message");
        channel.basicPublish(exchangeConfig.getName(), queueConfig.getRoutingKey(), messageConfig.isMandatory(),
                messageConfig.getMessageProperties(), message.getFormattedMessage().getBytes());
    }

    public AmqpConfig getAmqpConfig() {
        return amqpConfig;
    }

    public ExchangeConfig getExchangeConfig() {
        return exchangeConfig;
    }

    public QueueConfig getQueueConfig() {
        return queueConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    private void setupConnection() {
        try {
            if (!isConnectionOpen()) {
                LOGGER.info("Creating RabbitMQ connection");
                connection = createConnectionFactory(amqpConfig).newConnection();
            }
        } catch (IOException | KeyManagementException | NoSuchAlgorithmException | TimeoutException | URISyntaxException e) {
            LOGGER.error("Error establishing connection", e);
            throw new RuntimeException("Unable to create AMQP connection");
        }
    }

    private boolean isConnectionOpen() {
        return connection != null && connection.isOpen();
    }

    private void setupChannel() {
        try {
            if (isChannelOpen()) {
                return;
            } else if (isConnectionOpen()) {
                LOGGER.info("Creating RabbitMQ channel");
                channel = connection.createChannel();
                return;
            }
        } catch (IOException e) {
            LOGGER.error("Error creating channel", e);
            throw new RuntimeException("Unable to create AMQP channel");
        }

        throw new RuntimeException("A valid connection must be open before creating a channel");
    }

    private boolean isChannelOpen() {
        return channel != null && channel.isOpen();
    }

    private void registerShutdownCallback() {
        LOGGER.info("Registering appender shutdown callback");
        ((ShutdownCallbackRegistry) LogManager.getFactory()).addShutdownCallback(() -> {
            if (isConnectionOpen()) {
                try {
                    LOGGER.info("Closing RabbitMQ connection");
                    connection.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close connection", e);
                }
            }
            if (isChannelOpen()) {
                try {
                    LOGGER.info("Closing RabbitMQ channel");
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    LOGGER.error("Unable to close channel", e);
                }
            }
        });
    }

    private static ConnectionFactory createConnectionFactory(final AmqpConfig amqp) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        LOGGER.info("Creating RabbitMQ ConnectionFactory");
        ConnectionFactory cf = new ConnectionFactory();

        if (amqp.getTimeout() > 0) {
            cf.setConnectionTimeout(amqp.getTimeout());
        }

        if (amqp.getUri() != null) {
            cf.setUri(amqp.getUri());
            return cf;
        }

        if (amqp.getUser() != null) {
            cf.setUsername(amqp.getUser());
        }

        if (amqp.getPassword() != null) {
            cf.setPassword(amqp.getPassword());
        }

        if (amqp.getHost() != null) {
            cf.setHost(amqp.getHost());
        }

        if (amqp.getPort() != 0) {
            cf.setPort(amqp.getPort());
        }

        if (amqp.getVhost() != null) {
            cf.setVirtualHost(amqp.getVhost());
        }

        return cf;
    }

    private static class RabbitMQConfiguration {

        private final AmqpConfig amqpConfig;
        private final ExchangeConfig exchangeConfig;
        private final QueueConfig queueConfig;
        private final MessageConfig messageConfig;

        private RabbitMQConfiguration(final AmqpConfig amqpConfig, final ExchangeConfig exchangeConfig, final QueueConfig queueConfig, final MessageConfig messageConfig) {
            this.amqpConfig = amqpConfig;
            this.exchangeConfig = exchangeConfig;
            this.queueConfig = queueConfig;
            this.messageConfig = messageConfig;
        }
    }

    private static class RabbitMQAppenderManagerFactory implements ManagerFactory<RabbitMQAppenderManager, RabbitMQConfiguration> {

        @Override
        public RabbitMQAppenderManager createManager(final String name, final RabbitMQConfiguration config) {
            return new RabbitMQAppenderManager(name, config);
        }

    }
}
