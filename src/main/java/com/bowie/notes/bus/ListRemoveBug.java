package com.bowie.notes.bus;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Bowie on 2019/3/27 15:45
 *
 * 迭代器中需要删除一个集合中的某个元素引发的bug
 *
 **/
public class ListRemoveBug {

    public static void main(String[] args) {


        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);

        //wrong way
        for (Integer next : list) {
            if (next == 2) {
                list.remove(next); //special attention
            }
        }

        //right way
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next == 2) {
                iterator.remove(); //special attention
            }
        }

        //explain:
        //在迭代器的循环中.next()方法中有一个判断:
        //if (modCount != expectedModCount)  throw new ConcurrentModificationException();
        //在使用list.remove()方法的时候,因为对集合进行了修改所以modCount+1,而此时expectedModCount为0,
        //所以会抛出异常ConcurrentModificationException;
        //for-each的语法糖本质上是在使用迭代器

    }
}
