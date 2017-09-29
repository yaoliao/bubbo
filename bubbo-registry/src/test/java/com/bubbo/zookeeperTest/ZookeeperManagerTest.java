package com.bubbo.zookeeperTest;

import com.bubbo.zookeeper.ZookeeperManager;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL on 2017/9/28.
 */
public class ZookeeperManagerTest {

    @Test
    public void createNode(){
        ZookeeperManager manager = new ZookeeperManager("127.0.0.1:2181");
        manager.connect();
        manager.createNode("ZK_TEST_1");
        manager.createNode("ZK_TEST_2");
        List<String> list = manager.listChildren("/ZK_TEST");
        System.out.println(list.toString());
        manager.deleteNode("/ZK_TEST");
        List<String> list1 = manager.listChildren("/ZK_TEST");
        System.out.println(list1.toString());
    }

}
