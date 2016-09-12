package com.whitlaaa.log4j2.rabbitmq;

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

        Assert.assertFalse(config.isPersistent());
        Assert.assertTrue(config.isMandatory());
        Assert.assertEquals("application/json", config.getContentType());
    }
    
    @Test
    public void equals_SameValues_InstancesEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/json");

        Assert.assertEquals(config1, config2);
    }
    
    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/json");

        Assert.assertEquals(config1.hashCode(), config2.hashCode());
    }
    
    @Test
    public void equals_DifferentContentType_InstancesNotEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/xml");

        Assert.assertNotEquals(config1, config2);
    }
    
    @Test
    public void hashcode_DifferentContentType_HashcodesNotEqual() {
        MessageConfig config1 = MessageConfig.createMessageConfig(false, true, "application/json");
        MessageConfig config2 = MessageConfig.createMessageConfig(false, true, "application/xml");

        Assert.assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
