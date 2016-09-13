package com.whitlaaa.log4j2.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueueConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        QueueConfig config = QueueConfig.DEFAULT;

        assertNotNull(config);
    }

    @Test
    public void createQueueConfig_ValidValues_Created() {
        QueueConfig config = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        assertEquals("testname", config.getName());
        assertEquals("testkey", config.getRoutingKey());
        assertTrue(config.isDurable());
        assertFalse(config.isExclusive());
        assertFalse(config.isAutoDelete());
        assertTrue(config.isDeclare());
    }

    @Test
    public void equals_SameValues_InstancesEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        assertEquals(config1, config2);
    }

    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void equals_DifferentRoutingKey_InstancesNotEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "fakekey", true, false, false, true);

        assertNotEquals(config1, config2);
    }

    @Test
    public void hashcode_DifferentRoutingKey_HashcodesNotEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "fakekey", true, false, false, true);

        assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
