package com.whitlaaa.log4j2.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.MessageProperties;
import java.util.Objects;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "Messages", category = Node.CATEGORY, printObject = true)
public final class MessageConfig {

    public static final MessageConfig DEFAULT = new MessageConfig(false, false, "text/plain");

    private final boolean persistent;
    private final boolean mandatory;
    private final String contentType;

    private MessageConfig(boolean persistent, boolean mandatory, String contentType) {
        this.persistent = persistent;
        this.mandatory = mandatory;
        this.contentType = contentType;
    }

    @PluginFactory
    public static MessageConfig createMessageConfig(
            @PluginAttribute("persistent") boolean persistent,
            @PluginAttribute("mandatory") boolean mandatory,
            @PluginAttribute(value = "contentType", defaultString = "text/plain") String contentType) {
        return new MessageConfig(persistent, mandatory, contentType);
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public String getContentType() {
        return contentType;
    }

    public AMQP.BasicProperties getMessageProperties() {
        if (persistent) {
            return MessageProperties.MINIMAL_PERSISTENT_BASIC;
        }

        return MessageProperties.MINIMAL_BASIC;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.mandatory ? 1 : 0);
        hash = 59 * hash + (this.persistent ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.contentType);
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
        final MessageConfig other = (MessageConfig) obj;
        if (this.persistent != other.persistent) {
            return false;
        }
        if (this.mandatory != other.mandatory) {
            return false;
        }
        if (!Objects.equals(this.contentType, other.contentType)) {
            return false;
        }
        return true;
    }

}
