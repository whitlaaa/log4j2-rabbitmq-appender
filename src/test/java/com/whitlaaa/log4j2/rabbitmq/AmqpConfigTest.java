package com.whitlaaa.log4j2.rabbitmq;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AmqpConfigTest {

    @Test
    public void default_ValidState_DefaultInstanceCreated() {
        AmqpConfig config = AmqpConfig.DEFAULT;

        assertNotNull(config);
    }

    @Test
    public void createAmqpConfig_ValidValues_Created() {
        AmqpConfig config = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);

        assertEquals("testuri", config.getUri());
        assertEquals("guestuser", config.getUser());
        assertEquals("guestpassword", config.getPassword());
        assertEquals("testhost", config.getHost());
        assertEquals(5672, config.getPort());
        assertEquals("testvhost", config.getVhost());
        assertEquals(30, config.getTimeout());
    }

    @Test
    public void equals_SameValues_InstancesEqual() {
        AmqpConfig config1 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);
        AmqpConfig config2 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);

        assertEquals(config1, config2);
    }

    @Test
    public void hashcode_SameValues_HashcodesEqual() {
        AmqpConfig config1 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);
        AmqpConfig config2 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);

        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void equals_DifferentUri_InstancesNotEqual() {
        AmqpConfig config1 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);
        AmqpConfig config2 = AmqpConfig.createAmqpConfig("fakeuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);

        assertNotEquals(config1, config2);
    }

    @Test
    public void hashcode_DifferentUri_HashcodesNotEqual() {
        AmqpConfig config1 = AmqpConfig.createAmqpConfig("testuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);
        AmqpConfig config2 = AmqpConfig.createAmqpConfig("fakeuri", "guestuser", "guestpassword", "testhost", 5672, "testvhost", 30);

        assertNotEquals(config1.hashCode(), config2.hashCode());
    }
}
