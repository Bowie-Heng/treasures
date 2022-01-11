package com.bowie.notes.features.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Bowie on 2020/1/9 13:37
 **/
@Data
public class Person {

    private String firstName;

    private String middleName;

    private String age;

    private BigDecimal salary;

    private LocalDate date;

    public Person(String firstName, String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Person(String firstName, String middleName,String age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
    }

    public Person(String firstName, String middleName, String age, BigDecimal salary) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
        this.salary = salary;
    }

    public Person(String firstName, BigDecimal salary) {
        this.firstName = firstName;
        this.salary = salary;
    }

    public Person(String firstName, BigDecimal salary,LocalDate date ) {
        this.firstName = firstName;
        this.salary = salary;
        this.date = date;
    }
}
