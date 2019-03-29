package com.bowie.notes.reflection;

import com.bowie.notes.reflection.domain.User;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterNamesScanner;
import org.reflections.scanners.MethodParameterScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by Bowie on 2019/3/28 16:01
 **/
public class ReflectionsTest {

    @Test
    public void test() {

        //获取某个包下的所有对象的反射对象
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                //这里的意思是包含my.package的路径，包括以my.package开头的
                .addUrls(ClasspathHelper.forPackage("com.bowie.notes.reflection"))
                .setScanners(new ResourcesScanner(), new SubTypesScanner())
                //强制过滤只以下面的内容开头的
                .filterInputsBy(new FilterBuilder().includePackage("com.bowie.notes.reflection"))
        );

        //下面的这种写法与上面的写法没有差别
        //Reflections reflections = new Reflections("com.bowie.notes.reflection",new ResourcesScanner(),new SubTypesScanner());


        //通过反射对象获取继承了User.class的对象的class对象
        Set<Class<? extends User>> classSet = reflections.getSubTypesOf(User.class);

        //通过反射对象获取符合条件的resources
        Set<String> resources = reflections.getResources(Pattern.compile(".*\\.properties"));

        //获取到class对象之后实例化并赋值
        for (Class<? extends User> aClass : classSet) {
            try {
                User user = aClass.newInstance();
                user.setName("Bowie");
                user.setAge(1);
                System.out.println(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //获取到resources地址之后获取其中内容
        for (String resource : resources) {
            Properties properties = new Properties();
            try {
                properties.load(this.getClass().getResourceAsStream("/" + resource));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(properties.getProperty("name"));

        }
    }

    @Test
    public void someOtherTest() {

        Reflections reflections = new Reflections("com.bowie.notes.reflection",new MethodParameterScanner());

        //获取符合条件的所有方法
        Set<Method> methodsMatchParams = reflections.getMethodsMatchParams(String.class);

        Set<Method> methodsReturn = reflections.getMethodsReturn(String.class);

        Reflections reflections1 = new Reflections("com.bowie.notes.reflection",new MethodParameterNamesScanner());

        //获取方法参数名
        try {
            List<String> setAge = reflections1.getMethodParamNames(User.class.getMethod("setAge", Integer.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testClassPathHelper() {

        //获取类的url
        URL url = ClasspathHelper.forClass(User.class);
        System.out.println(url);

        //获取包下的所有类的url
        Collection<URL> urls = ClasspathHelper.forPackage("com.bowie.notes.reflection");
        for (URL u : urls) {
            System.out.println(u);
        }

        //获取根目录下的所有资源的路径
        Collection<URL> resources = ClasspathHelper.forResource(".");
        for (URL resource : resources) {
            System.out.println(resource);
        }

        //这三个都是打印的com.bowie.notes包的绝对路径的前缀
        // --> file:/E:/remoteGit/treasures/notes-basis/target/classes/


    }

}
