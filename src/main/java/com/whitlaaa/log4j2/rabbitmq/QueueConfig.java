package com.whitlaaa.log4j2.rabbitmq;

import java.util.Objects;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Plugin(name = "Queue", category = Node.CATEGORY, printObject = true)
public final class QueueConfig {

    public static final QueueConfig DEFAULT = new QueueConfig(null, "logging", true, false, false, false);

    private final String name;
    private final String routingKey;
    private final boolean durable;
    private final boolean exclusive;
    private final boolean autoDelete;
    private final boolean declare;

    private QueueConfig(String name, String routingKey, boolean durable, boolean exclusive, boolean autoDelete, boolean declare) {
        this.name = name;
        this.routingKey = routingKey;
        this.durable = durable;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.declare = declare;
    }

    @PluginFactory
    public static QueueConfig createQueueConfig(
            @PluginAttribute("name") @Required String name,
            @PluginAttribute(value = "routingKey", defaultString = "logging") String routingKey,
            @PluginAttribute(value = "durable", defaultBoolean = true) boolean durable,
            @PluginAttribute("exclusive") boolean exclusive,
            @PluginAttribute("autoDelete") boolean autoDelete,
            @PluginAttribute("declare") boolean declare) {
        return new QueueConfig(name, routingKey, durable, exclusive, autoDelete, declare);
    }

    public String getName() {
        return name;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public boolean isAutoDelete() {
        return autoDelete;
    }

    public boolean isDeclare() {
        return declare;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.routingKey);
        hash = 29 * hash + (this.durable ? 1 : 0);
        hash = 29 * hash + (this.exclusive ? 1 : 0);
        hash = 29 * hash + (this.autoDelete ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QueueConfig other = (QueueConfig) obj;
        if (this.durable != other.durable) {
            return false;
        }
        if (this.exclusive != other.exclusive) {
            return false;
        }
        if (this.autoDelete != other.autoDelete) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.routingKey, other.routingKey)) {
            return false;
        }
        return true;
    }
}
