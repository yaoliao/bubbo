package com.bubbo.zookeeper;

import com.bubbo.property.PropertyReader;
import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.log4j.Logger;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/9/28.
 */
public class ZookeeperManager {

    private static final Logger LOGGER = Logger.getLogger(ZookeeperManager.class);
    private static final PropertyReader READER = new PropertyReader("zookeeper.properties");
    public static final String ZK_REGISTRY_PATH = READER.get("ZK_REGISTRY_PATH");
    private static final Integer ZK_SESSION_TIMEOUT = Integer.valueOf(READER.get("ZK_SESSION_TIMEOUT"));
    private static final Integer ZK_RETRY_TIMES = Integer.valueOf(READER.get("ZK_RETRY_TIMES"));

    private String zkAddress;
    private CuratorFramework client;

    public ZookeeperManager(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public void connect() {
        client = CuratorFrameworkFactory.newClient(zkAddress, new RetryNTimes(ZK_RETRY_TIMES, ZK_SESSION_TIMEOUT));
        client.start();
        LOGGER.info("成功连接到Zookeeper");
    }

    public void close() {
        client.close();
        LOGGER.info("关闭Zookeeper连接");
    }

    public void createNode(String nodePath) {
        try {
            Stat stat = client.checkExists().forPath(ZK_REGISTRY_PATH);
            if (stat == null) {
                client.create().creatingParentsIfNeeded().forPath(ZK_REGISTRY_PATH);
            }
            Stat path = client.checkExists().forPath(ZK_REGISTRY_PATH + "/" + nodePath);
            if (path == null) {
                client.create().creatingParentsIfNeeded().forPath(ZK_REGISTRY_PATH + "/" + nodePath);
            }
            LOGGER.info("node [" + nodePath + "] 创建节点成功");
//            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ZookeeperManager] 创建节点失败");
        }
    }

    public List<String> listChildren(String nodePath) {
        checkGroupPath(nodePath);
        List<String> paths = new ArrayList<>();
        try {
            Stat stat = client.checkExists().forPath(nodePath);
            if (stat != null) {
                List<String> strings = client.getChildren().forPath(nodePath);
                paths.addAll(strings);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ZookeeperManager] 获取节点集合失败");
        }
        return paths;
    }

    public void deleteNode(String nodePath) {
        checkGroupPath(nodePath);
        try {
            List<String> list = client.getChildren().forPath(nodePath);
            for (String s : list) {
                client.delete().forPath(nodePath + "/" + s);
            }
            client.delete().forPath(nodePath);
            LOGGER.info("node [" + nodePath + "] 删除节点成功");
//            client.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("[ZookeeperManager] 删除节点失败");
        }
    }


    private void checkGroupPath(String nodePath) {
        if (Strings.isNullOrEmpty(nodePath)) {
            throw new IllegalArgumentException("[ZookeeperManager] nodePath 不能为空");
        }
        if (!nodePath.startsWith(ZK_REGISTRY_PATH)) {
            throw new IllegalArgumentException("[ZookeeperManager] modePath 格式有误 " + nodePath);
        }

    }

}
