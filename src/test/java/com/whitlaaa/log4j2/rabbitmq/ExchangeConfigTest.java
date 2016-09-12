package com.whitlaaa.log4j2.rabbitmq;

import org.junit.Assert;
import org.junit.Test;

public class ExchangeConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        ExchangeConfig config = ExchangeConfig.DEFAULT;

        Assert.assertNotNull(config);
    }

    @Test
    public void createExchangeConfig_ValidValues_Created() {
        ExchangeConfig config = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        Assert.assertEquals("testname", config.getName());
        Assert.assertEquals(ExchangeType.DIRECT, config.getType());
        Assert.assertFalse(config.isDurable());
        Assert.assertTrue(config.isDeclare());
    }
    
    @Test
    public void equals_SameValues_InstancesEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        Assert.assertEquals(config1, config2);
    }
    
    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        Assert.assertEquals(config1.hashCode(), config2.hashCode());
    }
    
    @Test
    public void equals_DifferentName_InstancesNotEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("fakename", ExchangeType.DIRECT, false, true);

        Assert.assertNotEquals(config1, config2);
    }
    
    @Test
    public void hashcode_DifferentName_HashcodesNotEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("fakename", ExchangeType.DIRECT, false, true);

        Assert.assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
