package com.whitlaaa.log4j2.rabbitmq;

import java.util.Objects;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "Amqp", category = Node.CATEGORY, printObject = true)
public final class AmqpConfig {

    public static final AmqpConfig DEFAULT = new AmqpConfig(null, null, null, null, 0, null, 0);

    private final String uri;
    private final String user;
    private final String password;
    private final String host;
    private final int port;
    private final String vhost;
    private final int timeout;

    private AmqpConfig(String uri, String user, String password, String host, int port, String vhost, int timeout) {
        this.uri = uri;
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.vhost = vhost;
        this.timeout = timeout;
    }

    @PluginFactory
    public static AmqpConfig createAmqpConfig(
            @PluginAttribute("uri") String uri,
            @PluginAttribute("user") String user,
            @PluginAttribute("password") String password,
            @PluginAttribute("host") String host,
            @PluginAttribute("port") int port,
            @PluginAttribute("vhost") String vhost,
            @PluginAttribute("timeout") int timeout) {
        return new AmqpConfig(uri, user, password, host, port, vhost, timeout);
    }

    public String getUri() {
        return uri;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getVhost() {
        return vhost;
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.uri);
        hash = 89 * hash + Objects.hashCode(this.user);
        hash = 89 * hash + Objects.hashCode(this.password);
        hash = 89 * hash + Objects.hashCode(this.host);
        hash = 89 * hash + this.port;
        hash = 89 * hash + Objects.hashCode(this.vhost);
        hash = 89 * hash + this.timeout;
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
        final AmqpConfig other = (AmqpConfig) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.uri, other.uri)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.host, other.host)) {
            return false;
        }
        if (!Objects.equals(this.vhost, other.vhost)) {
            return false;
        }
        if (this.timeout != other.timeout) {
            return false;
        }
        return true;
    }
}
