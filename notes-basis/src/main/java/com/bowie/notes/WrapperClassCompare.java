package com.bowie.notes;

/**
 * Created by Bowie on 2019/3/27 15:56
 *
 * Integer类比较的问题
 *
 **/
public class WrapperClassCompare {
    public static void main(String[] args) {

        Integer i1 = 1;
        Integer j1 = 1;

        System.out.println(i1.equals(j1)); //true
        System.out.println(i1==j1);//true

        Integer i2 = 128;
        Integer j2 = 128;

        System.out.println(i2.equals(j2)); //true
        System.out.println(i2==j2);//false

        //explain:
        //JVM在对待类似Integer a =1，这种类型的数据的时候会自动装箱，方式其实是调用Integer.valueOf方法
        //这个方法内容如下：
        //	  if (i >= IntegerCache.low && i <= IntegerCache.high)
        //            return IntegerCache.cache[i + (-IntegerCache.low)];
        //        return new Integer(i);
        //所以用==判断的时候，区间在-128->127的时候不会有问题，因为返回的是同一个对象
        //超过的时候就会new一个新的了

    }
}
