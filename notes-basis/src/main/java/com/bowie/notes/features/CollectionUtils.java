package com.bowie.notes.features;

import com.bowie.notes.features.entities.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Bowie on 2020/1/9 14:41
 * 集合的流操作
 * 在 Java 8 中, 集合接口有两个方法来生成流：
 * <p>
 * stream() − 为集合创建串行流。
 * <p>
 * parallelStream() − 为集合创建并行流。
 **/
public interface CollectionUtils {
    public static void main(String[] args) {

        //filter
        // 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        //针对String,还可以使用join的方式连接出字符串
        String filterString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(""));

        //map
        //map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());

        //limit forEach
        //limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据
        //forEach 方法用于迭代流中的每个数据。以下代码片段使用 forEach 输出了10个随机数
        //sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
        //forEach方法 还可以对Map使用
        Map<String, Person> personMap = new HashMap<>();
        personMap.put("one", new Person("Bowie", "one"));
        personMap.put("two", new Person("Bowie", "two"));
        personMap.forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v.getFirstName());
        });
        //sorted方法还可以对对象进行排序
        Person person1 = new Person("Bowie", "one", "1");
        Person person2 = new Person("Bowie", "two", "2");
        List<Person> peopleList = Arrays.asList(person1, person2);
        //正向排序
        peopleList.sort(Comparator.comparingInt(people -> Integer.valueOf(Optional.ofNullable(people.getAge()).orElse("0"))));
        //那么如果我们想反向的话呢?
        //下面的方法会报错
        //peopleList.sort(Comparator.comparingInt(people -> Integer.valueOf(Optional.ofNullable(people.getAge()).orElse("0"))).reversed());
        //下面的方式不符合我们的需求
        //peopleList.sort(Comparator.comparing(Person::getAge).reversed());
        //可以这么做 1
        Comparator<Person> personComparator = Comparator.comparingInt(people -> Integer.valueOf(Optional.ofNullable(people.getAge()).orElse("0")));
        peopleList.sort(personComparator.reversed());
        //也可以这么做 2
        peopleList.sort(Comparator.comparingInt(people -> {
            Person person = (Person) people;
            return Integer.valueOf(Optional.ofNullable(person.getAge()).orElse("0"));
        }).reversed());

        //@TODO parallel Collectors实现集合转集合 集合转Map等操作


    }

}
