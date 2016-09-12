package com.whitlaaa.log4j2.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.status.StatusData;
import org.apache.logging.log4j.status.StatusLogger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class RabbitMQAppenderTest {

    private static final String TEST_APPENDER_NAME = "test-appender";

    private RabbitMQAppender appender;
    private RabbitMQAppenderManager mockManager;
    private static final Logger LOGGER = LogManager.getLogger(RabbitMQAppenderTest.class);

    @Before
    public void setup() {
        mockManager = mock(RabbitMQAppenderManager.class);
        appender = new RabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), false, mockManager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ctor_NullManager_IllegalArgumentException() {
        new RabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), false, null);
    }

    @Test
    public void createRabbitMQAppender_ValidValues_AppenderCreated() {
        RabbitMQAppender ra = RabbitMQAppender.createRabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), true,
                AmqpConfig.DEFAULT, ExchangeConfig.DEFAULT, QueueConfig.DEFAULT, MessageConfig.DEFAULT);

        assertNotNull(ra);
    }

    @Test
    public void createRabbitMQAppender_NullAmqpConfig_HasDefaultAmqpConfig() {
        RabbitMQAppender ra = RabbitMQAppender.createRabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), true,
                null, ExchangeConfig.DEFAULT, QueueConfig.DEFAULT, MessageConfig.DEFAULT);

        assertEquals(AmqpConfig.DEFAULT, ra.getManager().getAmqpConfig());
    }

    @Test
    public void createRabbitMQAppender_NullExchangeConfig_HasDefaultExchanageConfig() {
        RabbitMQAppender ra = RabbitMQAppender.createRabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), true,
                AmqpConfig.DEFAULT, null, QueueConfig.DEFAULT, MessageConfig.DEFAULT);

        assertEquals(ExchangeConfig.DEFAULT, ra.getManager().getExchangeConfig());
    }

    @Test
    public void createRabbitMQAppender_NullQueueConfig_HasDefaultQueueConfig() {
        RabbitMQAppender ra = RabbitMQAppender.createRabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), true,
                AmqpConfig.DEFAULT, ExchangeConfig.DEFAULT, null, MessageConfig.DEFAULT);

        assertEquals(QueueConfig.DEFAULT, ra.getManager().getQueueConfig());
    }

    @Test
    public void createRabbitMQAppender_NullMessageConfig_HasDefaultMessageConfig() {
        RabbitMQAppender ra = RabbitMQAppender.createRabbitMQAppender(TEST_APPENDER_NAME, null, PatternLayout.createDefaultLayout(), true,
                AmqpConfig.DEFAULT, ExchangeConfig.DEFAULT, QueueConfig.DEFAULT, null);

        assertEquals(MessageConfig.DEFAULT, ra.getManager().getMessageConfig());
    }
    
    @Test
    public void logger_FromConfiguration_CorrectAppender() {
        Map<String, Appender> appenders = getLoggerAppenders();
        
        assertEquals(1, appenders.size());
        
        RabbitMQAppender rabbitAppender = (RabbitMQAppender) appenders.get("RabbitMQ");
        assertEquals(RabbitMQAppender.class, rabbitAppender.getClass());
        assertEquals("test-queue", rabbitAppender.getManager().getQueueConfig().getName());
    }

    @Test
    public void append_ValidEvent_Success() {
        LogEvent event = buildLogEvent(LOGGER.getName(), Level.ERROR, "test message");

        appender.append(event);
    }

    @Test
    public void append_ManagerPublishThrows_StatusErrorLogged() throws IOException {
        Mockito.doThrow(IOException.class).when(mockManager).publish(any(Message.class));
        LogEvent event = buildLogEvent(LOGGER.getName(), Level.ERROR, "test message");
        StatusLogger statusLogger = StatusLogger.getLogger();

        appender.append(event);

        List<StatusData> statusData = statusLogger.getStatusData();
        assertEquals(1, statusData.size());
        assertEquals(Level.ERROR, statusData.get(0).getLevel());
        assertEquals(IOException.class, statusData.get(0).getThrowable().getClass());
        assertTrue(statusData.get(0).getMessage().getFormattedMessage().startsWith("Unable to publish message to RabbitMQ"));
    }

    private static LogEvent buildLogEvent(String name, Level level, String message) {
        return new Log4jLogEvent.Builder()
                .setLoggerName(name)
                .setLoggerFqcn(RabbitMQAppenderTest.class.getName())
                .setLevel(level)
                .setMessage(new SimpleMessage(message))
                .build();
    }
    
    private static Map<String, Appender> getLoggerAppenders() {
        LoggerContext context = (LoggerContext) LogManager.getContext(true);
        return context.getConfiguration().getAppenders();
    }
}
