package com.bowie.notes.framework.util;

import java.util.List;

/**
 * Created by Bowie on 2019/5/10 17:15
 **/
public class Detect {

    //返回集合中第一个元素
    public static <T> T firstElement(List<T> list) {
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
