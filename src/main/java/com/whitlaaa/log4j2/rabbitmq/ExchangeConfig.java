package com.whitlaaa.log4j2.rabbitmq;

import java.util.Objects;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

@Plugin(name = "Exchange", category = Node.CATEGORY, printObject = true)
public final class ExchangeConfig {

    public static final ExchangeConfig DEFAULT = new ExchangeConfig("amq.direct", ExchangeType.DIRECT, true, false);

    private final String name;
    private final ExchangeType type;
    private final boolean durable;
    private final boolean declare;

    private ExchangeConfig(String name, ExchangeType type, boolean durable, boolean declare) {
        this.name = name;
        this.type = type;
        this.durable = durable;
        this.declare = declare;
    }

    @PluginFactory
    public static ExchangeConfig createExchangeConfig(
            @PluginAttribute("name") @Required String name,
            @PluginAttribute("type") @Required ExchangeType type,
            @PluginAttribute("durable") boolean durable,
            @PluginAttribute("declare") boolean declare) {
        return new ExchangeConfig(name, type, durable, declare);
    }

    public String getName() {
        return name;
    }

    public ExchangeType getType() {
        return type;
    }

    public boolean isDurable() {
        return durable;
    }

    public boolean isDeclare() {
        return declare;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + (this.durable ? 1 : 0);
        hash = 79 * hash + (this.declare ? 1 : 0);
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
        final ExchangeConfig other = (ExchangeConfig) obj;
        if (this.durable != other.durable) {
            return false;
        }
        if (this.declare != other.declare) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
}
