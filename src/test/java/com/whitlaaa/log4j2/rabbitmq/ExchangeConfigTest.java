package com.whitlaaa.log4j2.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ExchangeConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        ExchangeConfig config = ExchangeConfig.DEFAULT;

        assertNotNull(config);
    }

    @Test
    public void createExchangeConfig_ValidValues_Created() {
        ExchangeConfig config = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        assertEquals("testname", config.getName());
        assertEquals(ExchangeType.DIRECT, config.getType());
        assertFalse(config.isDurable());
        assertTrue(config.isDeclare());
    }

    @Test
    public void equals_SameValues_InstancesEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        assertEquals(config1, config2);
    }

    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);

        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void equals_DifferentName_InstancesNotEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("fakename", ExchangeType.DIRECT, false, true);

        assertNotEquals(config1, config2);
    }

    @Test
    public void hashcode_DifferentName_HashcodesNotEqual() {
        ExchangeConfig config1 = ExchangeConfig.createExchangeConfig("testname", ExchangeType.DIRECT, false, true);
        ExchangeConfig config2 = ExchangeConfig.createExchangeConfig("fakename", ExchangeType.DIRECT, false, true);

        assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
