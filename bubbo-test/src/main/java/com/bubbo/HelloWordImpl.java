package com.bubbo;


import com.bubbo.annotation.BubboService;

import java.util.Date;

/**
 * Created by DELL on 2017/9/29.
 */
@BubboService(HelloWord.class)
public class HelloWordImpl implements HelloWord {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public Message send(int ID, String name, String msg) {
        return new Message(ID, name, msg, new Date());
    }
}
