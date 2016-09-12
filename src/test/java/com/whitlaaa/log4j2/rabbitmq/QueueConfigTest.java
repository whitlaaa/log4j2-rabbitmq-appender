package com.whitlaaa.log4j2.rabbitmq;

import org.junit.Assert;
import org.junit.Test;

public class QueueConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        QueueConfig config = QueueConfig.DEFAULT;

        Assert.assertNotNull(config);
    }

    @Test
    public void createQueueConfig_ValidValues_Created() {
        QueueConfig config = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        Assert.assertEquals("testname", config.getName());
        Assert.assertEquals("testkey", config.getRoutingKey());
        Assert.assertTrue(config.isDurable());
        Assert.assertFalse(config.isExclusive());
        Assert.assertFalse(config.isAutoDelete());
        Assert.assertTrue(config.isDeclare());
    }

    @Test
    public void equals_SameValues_InstancesEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        Assert.assertEquals(config1, config2);
    }

    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);

        Assert.assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void equals_DifferentRoutingKey_InstancesNotEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "fakekey", true, false, false, true);

        Assert.assertNotEquals(config1, config2);
    }

    @Test
    public void hashcode_DifferentRoutingKey_HashcodesNotEqual() {
        QueueConfig config1 = QueueConfig.createQueueConfig("testname", "testkey", true, false, false, true);
        QueueConfig config2 = QueueConfig.createQueueConfig("testname", "fakekey", true, false, false, true);

        Assert.assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
