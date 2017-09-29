package com.bubbo;

/**
 * Created by DELL on 2017/9/27.
 */
public class Clazz {

    private Integer ID;
    private String name;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }
}
