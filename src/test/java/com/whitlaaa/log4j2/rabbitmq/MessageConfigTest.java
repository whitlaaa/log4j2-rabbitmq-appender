package com.whitlaaa.log4j2.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.rabbitmq.client.MessageProperties;
import org.junit.Assert;
import org.junit.Test;

public class MessageConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        MessageConfig config = MessageConfig.DEFAULT;

        Assert.assertNotNull(config);
    }

    @Test
    public void createMessageConfig_ValidValues_Created() {
        MessageConfig config = MessageConfig.createMessageConfig(false, true, "application/json");

        assertFalse(config.isPersistent());
        assertTrue(config.isMandatory());
        assertEquals("application/json", config.getContentType());
    }

    @Test
    public void createMessageConfig_Persistent_CorrectAmqpProperties() {
        MessageConfig config = MessageConfig.createMessageConfig(true, true, "application/json");

        assertEquals(MessageProperties.MINIMAL_PERSISTENT_BASIC, config.getMessageProperties());
    }
    
    @Test
    public void createMessageConfig_NonPersistent_CorrectAmqpProperties() {
        MessageConfig config = MessageConfig.createMessageConfig(false, true, "application/json");

        assertEquals(MessageProperties.MINIMAL_BASIC, config.getMessageProperties());
    }

    @Test
    public void equals_SameValues_InstancesEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/json");

        assertEquals(config1, config2);
    }

    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/json");

        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void equals_DifferentContentType_InstancesNotEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/xml");

        assertNotEquals(config1, config2);
    }

    @Test
    public void hashcode_DifferentContentType_HashcodesNotEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/xml");

        assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
