package com.bowie.notes.reflection.domain;

import java.util.Date;

/**
 * Created by Bowie on 2019/3/28 15:29
 **/
public class User {

    public User(){}

    private String name;

    private Integer age;

    private Date birthDay;

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "name : " + name + ",age : " + age;
    }
}
