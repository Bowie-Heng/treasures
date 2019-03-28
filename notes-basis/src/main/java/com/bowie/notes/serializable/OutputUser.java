package com.bowie.notes.serializable;

import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Bowie on 2019/3/27 18:20
 **/
public class OutputUser {

    public static void main(String[] args) {
        ObjectOutputStream oos = null;

        User user = new User();
        user.setAge(27);
        user.setName("Bowie");

        try {

            oos = new ObjectOutputStream(new FileOutputStream("tempFile"));

            oos.writeObject(user);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(oos);
        }

    }
}
