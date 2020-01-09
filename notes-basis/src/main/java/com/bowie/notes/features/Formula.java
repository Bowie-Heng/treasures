package com.bowie.notes.features;

/**
 * Created by Bowie on 2020/1/7 16:21
 * Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展方法
 *
 * 为什么要有这个特性？
 *
 * 首先，之前的接口是个双刃剑，
 * 好处是面向抽象而不是面向具体编程，
 * 缺陷是，当需要修改接口时候，需要修改全部实现该接口的类，
 * 目前的 java 8 之前的集合框架没有 foreach 方法，
 * 通常能想到的解决办法是在JDK里给相关的接口添加新的方法及实现。
 * 然而，对于已经发布的版本，是没法在给接口添加新方法的同时不影响已有的实现。所以引进的默认方法。
 * 他们的目的是为了解决接口的修改与现有的实现不兼容的问题。
 **/
public interface Formula {

    //接口可以直接定义静态方法了
    static void say(double result){
        System.out.println(result);
    }

    double calculate(int a);

    //default的方法可以由接口的具体实现直接调用
    /********New*******/
    default double sqrt(int a) {
        return Math.sqrt(a);
    }

    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };
        Formula.say(formula.sqrt(16));
        Formula.say(formula.calculate(100));
    }
}
