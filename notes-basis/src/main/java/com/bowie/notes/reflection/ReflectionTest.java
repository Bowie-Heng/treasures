package com.bowie.notes.reflection;

import com.bowie.notes.reflection.domain.User;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Bowie on 2019/3/29 14:20
 **/
public class ReflectionTest {

    @Test
    public void testBasis(){

        try {

            User user = new User();
            //这里会触发类的初始化，classloader.load方法则不会
            Class<?> userClass = Class.forName("com.bowie.notes.reflection.domain.User");
//            Class<? extends User> userClass = user.getClass();
            //获取所有方法
            Method[] methods = userClass.getMethods();

            //获取特定的某个方法
            Method setName = userClass.getMethod("setName", String.class);

            //获取返回类型
            Class<?> returnType = setName.getReturnType();

            //获取入参类型
            Class<?>[] parameterTypes = setName.getParameterTypes();

            //获取注解
            Annotation[] annotations = setName.getAnnotations();

            setName.invoke(user, "Bowie");

            System.out.println(user);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
