package com.bowie.notes.features;

/**
 * Created by Bowie on 2020/1/7 16:44
 * 函数式接口和lambda表达式
 **/
@FunctionalInterface
public interface Converter<F,T> {

    //如果想使用函数式接口,这里的抽象方法只能有一个
    T convert(F from);

    //但是default的方法可以有多个
    default Integer convert2(String from) {
        return Integer.valueOf(from);
    }

    public static void main(String[] args) {
        //这里可以将接口中唯一的抽象方法指向一个具体的实现
        Converter<String, Integer> convert = Integer::valueOf;
        //然后使用该
        System.out.println(convert.convert2("321"));
        System.out.println(convert.convert("123"));
    }


}
