package com.bubbo;

import java.util.Date;

/**
 * Created by DELL on 2017/9/29.
 */
public class Message {

    private int ID;
    private String name;
    private String msg;
    private Date sendTime;

    public Message() {
    }

    public Message(int ID, String name, String msg, Date sendTime) {
        this.ID = ID;
        this.name = name;
        this.msg = msg;
        this.sendTime = sendTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }
}
