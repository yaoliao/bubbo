package com.bubbo;

import com.bubbo.property.PropertyReader;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Created by DELL on 2017/9/28.
 */
public class PropertyTest {

    private static final Logger LOGGER = Logger.getLogger(PropertyTest.class);

    private static final String PATH = "bubboTest.properties";

    private static final String BUBBO = "zookeeper.properties";

    @Test
    public void propertiesTest() {
        PropertyReader reader = new PropertyReader(BUBBO);
        String name = reader.get("ZK_SESSION_TIMEOUT");
        System.out.println("ZK_SESSION_TIMEOUT = " + name);
        String age = reader.getByIO("ZK_REGISTRY_PATH");
        System.out.println("ZK_REGISTRY_PATH = " + age);

    }

    @Test
    public void log(){
        LOGGER.debug("debug ========");
        LOGGER.info("info ========");
        LOGGER.error("error =========");
    }

}
