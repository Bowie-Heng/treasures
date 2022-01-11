package com.bowie.notes.features;

import com.bowie.notes.features.entities.Person;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class CollectionUtils {

    @Test
    public void testFilter() {
        //filter
        // 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.forEach(System.out::println);

        //针对String,还可以使用join的方式连接出字符串
        String filterString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(""));
        System.out.println(filterString);

        //anyMatch allMatch noneMatch max min count 方法都类似下面这种 就不一一列举了
        System.out.println(strings.stream().anyMatch(s -> s.contains("a")));

    }

    @Test
    public void testMapAndFlatMap() {
        //map
        //map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        // 获取对应的平方数
        List<Integer> squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        for (Integer number : squaresList) {
            System.out.println(number);
        }

        //flatMap 不好解释
        //我们用List中放list来测试一下
        List<Integer> numbers1 = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
        List<Integer> numbers2 = Arrays.asList(2, 2, 2, 2, 2, 2, 2);
        List<List<Integer>> numberList = new ArrayList<>();
        numberList.add(numbers1);
        numberList.add(numbers2);
        numberList.stream().flatMap(numberList1 -> Arrays.stream(numberList1.toArray(new Integer[]{})).map(number -> number + 1)).forEach(number -> System.out.println("flatMap结果 : " + number));
        numberList.stream().map(numberList1 -> Arrays.stream(numberList1.toArray(new Integer[]{})).map(number -> number + 1)).forEach(number -> System.out.println("map结果 : " + number));

    }

    @Test
    public void testLimitSort() {

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
    }

    @Test
    public void testParallel() {
        //parallel 并发的流操作
        //要用线程安全的list
        List<Integer> ids = Collections.synchronizedList(Lists.newArrayList());
        for (int i = 0; i < 10; i++) {
            ids.add(i);
        }
        ids.parallelStream().forEach(id -> System.out.println("id = " + id));
    }

    @Test
    public void testListToMap() {
        //使用groupBy来完成List转Map
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Bowie", "Heng", "1"));
        personList.add(new Person("Bowie", "Heng", "1"));
        personList.add(new Person("Bowie", "Heng", "3"));
        Map<String, List<Person>> collect = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        collect.forEach((k, v) -> {
            System.out.println("key = " + k);
            v.forEach(System.out::println);
        });
    }

    @Test
    public void testReduce(){
        //reduce 操作可以实现从Stream中生成一个值，其生成的值不是随意的，
        // 而是根据指定的计算模型。比如，之前提到count、min和max方
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers1.stream().reduce((acc, item) -> {
            System.out.println("不带 identity acc = " + acc);
            acc += item;
            return acc;
        }).ifPresent(System.out::print);

        //与第一个方法不同的是其会接受一个identity参数，用来指定Stream循环的初始值。如果Stream为空，就直接返回该值.
        List<Integer> numbers2 = Arrays.asList(1,2 , 3, 4, 5, 6);
        System.out.println(numbers2.stream().reduce(3,(acc, item) -> {
            System.out.println("带 identity acc = " + acc);
            acc += item;
            return acc;
        }));


        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Bowie", new BigDecimal(100)));
        personList.add(new Person("Lily", new BigDecimal(300)));

        //计算总工资
        BigDecimal salarySum = personList.stream().map(Person::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("总工资:" + salarySum);

        //计算公司每天的工资发放，并且按日期排序
        //Collectors.groupingBy是按哪种方式组织map
        //第一个参数是map的key的function，第二个参数是生成map的function，第三个参数是value的function
        TreeMap<LocalDate, BigDecimal> treeMap = personList.stream().collect(Collectors.groupingBy(Person::getDate, TreeMap::new, Collectors.reducing(BigDecimal.ZERO, Person::getSalary, BigDecimal::add)));
        System.out.println(treeMap);

        //统计一下有工资的员工有多少个
        //Collectors.groupingBy传俩参数，默认map的生成规则是hashMap，第二个参数可以传入 Collectors的一些默认方法，这里意思是统计，类似于sql中的count关键字
        Map<String, Long> count = personList.stream().filter(person -> person.getSalary() != null).collect(Collectors.groupingBy(Person::getFirstName, Collectors.counting()));
        System.out.println(count);
    }


}
