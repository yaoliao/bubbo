package com.bubbo.zookeeper;

import org.apache.log4j.Logger;

/**
 * Created by DELL on 2017/9/28.
 */
public class ZkSerRegistry {

    private static final Logger LOGGER = Logger.getLogger(ZkSerRegistry.class);

    private ZookeeperManager manager;

    public ZkSerRegistry(ZookeeperManager manager) {
        this.manager = manager;
    }

    public void register(String host, Integer port) {
        manager.createNode(host + ":" + port);
        LOGGER.info("注册 [" + host + ":" + port + "] 到服务成功");
    }

    public void init() {
        manager.connect();
        manager.deleteNode(ZookeeperManager.ZK_REGISTRY_PATH);
        LOGGER.info("初始化注册列表，删除节点 :" + ZookeeperManager.ZK_REGISTRY_PATH);
    }
}
