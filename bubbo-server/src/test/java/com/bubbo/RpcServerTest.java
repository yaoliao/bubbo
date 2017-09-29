package com.bubbo;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by DELL on 2017/9/29.
 */
public class RpcServerTest {

    @Test
    public void server(){

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("server-Application.xml");
        Object rpcServer = context.getBean("rpcServer");
    }

}
