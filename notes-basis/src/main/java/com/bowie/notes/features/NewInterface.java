package com.bowie.notes.features;

import com.bowie.notes.features.entities.Person;

import java.sql.SQLOutput;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Bowie on 2020/1/7 17:40
 * 一些新接口的使用
 **/
public interface NewInterface {

    public static void main(String[] args) {

        //Predicate 接口只有一个参数，返回boolean类型。
        // 该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如： 与，或，非）
        //下面这段代码可以表示 : 字符串不为空,并且字符串所代表的数字是整数
        Predicate<String> predicate = s -> s.length() > 0;
        Predicate<String> and = predicate.and(s -> Integer.valueOf(s) > 0);
        System.out.println(and.test("2"));

        //Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, Integer> andThen = toInteger.andThen(s -> s + 1);
        System.out.println(andThen.apply("1"));

        //Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
        Supplier<String> stringSupplier = String::new;
        stringSupplier.get();

        //Consumer 接口表示执行在单个参数上的操作
        Consumer<Person> greeter = person -> System.out.println("Hello " + person.getFirstName());
        greeter.accept(new Person("Bowie",""));

        //Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
        //Optional 类的引入很好的解决空指针异常。
        //ofNullAble允许传入null,of不允许,会抛出空指针异常
        Person person1 = new Person("Bowie", "One");
        Person person2 = new Person("Bowie", "Two");
        Optional<Person> one = Optional.of(person1);
        Optional<Person> two = Optional.ofNullable(person2);
        //以下是optional常用方法,就不一一列举了
        boolean present = one.isPresent();//是否存在
        Person person = one.orElse(new Person("Bowie", "other"));//如果one不存在,就返回other
        one.ifPresent(person3 -> System.out.println(person3.getFirstName()));


    }
}
