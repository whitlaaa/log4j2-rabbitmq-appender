package com.whitlaaa.log4j2.rabbitmq;

import java.io.IOException;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.io.Serializable;

@Plugin(name = "RabbitMQ", category = "Core", elementType = "appender", printObject = true)
public final class RabbitMQAppender extends AbstractAppender {

    private final RabbitMQAppenderManager manager;

    protected RabbitMQAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, RabbitMQAppenderManager manager) {
        super(name, filter, layout, ignoreExceptions);
        
        if (manager == null) {
            throw new IllegalArgumentException("The provided RabbitMQAppenderManager is null");
        }
        
        this.manager = manager;
    }

    @PluginFactory
    public static RabbitMQAppender createRabbitMQAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filters") Filter filter,
            @PluginElement("Layout") Layout layout,
            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
            @PluginElement("Amqp") AmqpConfig amqp,
            @PluginElement("Exchange") ExchangeConfig exchange,
            @PluginElement("Queue") QueueConfig queue,
            @PluginElement("Messages") MessageConfig messages) {

        AmqpConfig ac = amqp == null ? AmqpConfig.DEFAULT : amqp;
        ExchangeConfig ec = exchange == null ? ExchangeConfig.DEFAULT : exchange;
        QueueConfig qc = queue == null ? QueueConfig.DEFAULT : queue;
        MessageConfig mc = messages == null ? MessageConfig.DEFAULT : messages;

        RabbitMQAppenderManager manager = RabbitMQAppenderManager.getManager(name, ac, ec, qc, mc);
        return new RabbitMQAppender(name, filter, layout, ignoreExceptions, manager);
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            manager.publish(logEvent.getMessage());
        } catch (IOException e) {
            LOGGER.error("Unable to publish message to RabbitMQ: " + logEvent.toString(), e);
        }
    }
    
    public RabbitMQAppenderManager getManager() {
        return manager;
    }
}
