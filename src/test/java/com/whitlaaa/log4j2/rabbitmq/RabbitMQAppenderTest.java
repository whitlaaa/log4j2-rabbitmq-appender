package com.whitlaaa.log4j2.rabbitmq;

import org.apache.logging.log4j.core.layout.PatternLayout;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;

public class RabbitMQAppenderTest {

    private static final String TEST_APPENDER_NAME = "test-appender";

    private RabbitMQAppender appender;
    private RabbitMQAppenderManager mockManager;

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
}
