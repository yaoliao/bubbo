package com.bubbo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 2017/9/27.
 */
public class KryoTest {

    @Test
    public void serialize(){
        Student student = get();
        byte[] bytes = SerializerFactory.load().serialize(student);
        System.out.println(Arrays.toString(bytes));
        Student s = SerializerFactory.load().deserialize(bytes, Student.class);
        System.out.println(s.toString());

        System.out.println("===================");

    }


    private Student get(){

        Clazz clazz = new Clazz();
        clazz.setID(123);
        clazz.setName("这是Clazz");
        Clazz clazz1 = new Clazz();
        clazz1.setID(456);
        clazz1.setName("这是Clazz1");

        Student s = new Student();
        s.setInteger(1);
        s.setString("这是String");
        s.setaDouble(233.00D);
        s.setClazz(clazz);
        HashMap<String, String> map = new HashMap<>();
        map.put("这是map1","这是map11");
        map.put("这是map2","这是map22");
        s.setMap(map);
        List<Clazz> list = new ArrayList<>();
        list.add(clazz);
        list.add(clazz1);
        s.setClazzList(list);
        s.setDate(new Date());

        return s;
    }

}
