package com.bubbo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2017/9/27.
 */
public class Student {

    private Integer integer;
    private String string;
    private Double aDouble;
    private Map<String, String> map;
    private Clazz clazz;
    private List<Clazz> clazzList;
    private Date date;

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public List<Clazz> getClazzList() {
        return clazzList;
    }

    public void setClazzList(List<Clazz> clazzList) {
        this.clazzList = clazzList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student{" +
                "integer=" + integer +
                ", string='" + string + '\'' +
                ", aDouble=" + aDouble +
                ", map=" + map +
                ", clazz=" + clazz +
                ", clazzList=" + clazzList +
                ", date=" + date +
                '}';
    }
}
