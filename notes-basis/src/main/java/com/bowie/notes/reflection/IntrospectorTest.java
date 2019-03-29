package com.bowie.notes.reflection;

import com.bowie.notes.reflection.domain.User;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by Bowie on 2019/3/28 15:36
 *
 * Introspector的使用和apache BeanUtils的使用
 *
 *
 **/
public class IntrospectorTest {

    @Test
    public void test(){

        try {

            User user = new User();

            //Introspect on a Java Bean and learn about all its properties, exposed
            //methods, and events.
            BeanInfo beanInfo = Introspector.getBeanInfo(User.class);

            //Returns descriptors for all properties of the bean.
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            //可以根据返回的属性描述做一下事情，例如：
            for (PropertyDescriptor pd : propertyDescriptors) {

                System.out.println(pd.getName());

                System.out.println(pd.getPropertyType());

                Method writeMethod = pd.getWriteMethod();

                if (pd.getPropertyType().equals(String.class)) {
                    writeMethod.invoke(user, "Bowie");
                    Method readMethod = pd.getReadMethod();
                    System.out.println(readMethod.invoke(user, null));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBeanUtils() {
        User user = new User();
        try {
            BeanUtils.setProperty(user,"age",1);
            BeanUtils.setProperty(user,"name","Bowie");
            System.out.println(user);

            String name = BeanUtils.getProperty(user, "name");
            System.out.println(name);

            //也可以赋值非基本数据类型和String的内容
            BeanUtils.setProperty(user,"birthDay",new Date());
            String birthDay = BeanUtils.getProperty(user, "birthDay");
            System.out.println(birthDay);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
