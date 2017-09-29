package com.bubbo.zookeeper;

import com.bubbo.property.RandomGenerator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by DELL on 2017/9/28.
 */
public class ZkSerDiscovery {

    private static final Logger LOGGER = Logger.getLogger(ZkSerDiscovery.class);

    private ZookeeperManager manager;

    public ZkSerDiscovery(ZookeeperManager manager) {
        this.manager = manager;
    }

    public String discover() {
        manager.connect();
        List<String> services = manager.listChildren(ZookeeperManager.ZK_REGISTRY_PATH);
        if (services == null || services.size() == 0) {
            LOGGER.info("当前无服务");
            return null;
        }
        Integer randInt = RandomGenerator.randInt(0, services.size() - 1);
        LOGGER.info("当前选择服务 [" + randInt + "]");
        return services.get(randInt);
    }
}
