package com.bowie.notes.features.entities;

import lombok.Data;

/**
 * Created by Bowie on 2020/1/9 13:37
 **/
@Data
public class Person {

    private String firstName;

    private String middleName;

    private String age;

    public Person(String firstName, String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Person(String firstName, String middleName,String age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
    }
}
