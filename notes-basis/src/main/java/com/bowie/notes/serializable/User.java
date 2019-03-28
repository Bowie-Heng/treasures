package com.bowie.notes.serializable;

import java.io.Serializable;

/**
 * Created by Bowie on 2019/3/27 18:18
 **/
public class User implements Serializable {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name = " + name + ",age = " + age;
    }
}
