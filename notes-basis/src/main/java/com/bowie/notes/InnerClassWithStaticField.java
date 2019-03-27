package com.bowie.notes;

/**
 * Created by Bowie on 2019/3/27 16:25
 *
 * 非静态内部类中不能有static属性的变量，却可以有static final属性的变量
 *
 **/
public class InnerClassWithStaticField {

    private int o = 1;

    class Inner {

        //static int i = 1;

        //static int i = new Integer(1);

        //static Date date = new Date();

        //static int i = 1;

        //static String s = 1;

        //------上面的定义会编译错误---

        //下面的定义不会报错
        static final int i = 1;

        static final String s = "静态内容";

        String v = "非静态内容";

        //内部类中想要返回外部类的属性，需要以如下方式访问
        int getOuterField() {
            return InnerClassWithStaticField.this.o;
        }


    }

    public static void main(String[] args) {

        //获取内部类成员变量的方式
        InnerClassWithStaticField outer = new InnerClassWithStaticField();
        Inner inner = outer.new Inner();
        System.out.println(inner.v);

        //获取内部类静态变量的方式
        System.out.println(Inner.s);

    }

    //explain:
    //static修饰的变量是类变量，是全局唯一的，而我们想要创建内部类，必须是要有外部类的对象，通过外部类对象创建的。
    //可以理解为他是对象的成员变量，而不是全局唯一的。
    //所以内部类不允许有静态内容，不然可能会出现通过一个外部对象操作内部对象数据的时候影响其他外部类的内部类数据，这是违反面向对象思想的。

    //那为什么加一个final就可以了呢？
    //final关键字表示这个变量在编译期过后是不可改变的，所以不会存在上面说的那种对一个外部对象的内部对象的数据进行操作的时候影响其他外部对象的内部对象

    //那为什么只能用在String或者是基本数据类型呢？
    //而修饰对象上的时候，表示对象的引用不可变，但是他的内容还是可变的，所以普通对象还是会出现之前提到的问题。而基本数据类型和String是完全不可变的，不会存在普通对象的问题。

}
