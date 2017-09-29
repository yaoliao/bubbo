package com.bubbo;

import com.bubbo.proxy.RpcProxy;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by DELL on 2017/9/29.
 */
public class RpcClientTest {

    @Test
    public void client() {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("client-Application.xml");
        RpcProxy proxy = (RpcProxy) context.getBean("proxy");
        HelloWord helloWord = (HelloWord) proxy.newProxy(HelloWord.class);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {  //测试TCP拆包问题
            sb.append("Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack Jack ").append(i);
        }
        String s = helloWord.hello(sb.toString());
        System.out.println(s);

        System.out.println("======================================================");

        Message message = helloWord.send(13, "Rose", "you jump I jump");
        System.out.println(message.toString());

    }

}
