package com.bowie.notes.serializable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by Bowie on 2019/3/28 10:01
 **/
public class InputUser {
    public static void main(String[] args) {

        File file = new File("tempFile");

        ObjectInputStream ois = null;

        try {

            ois = new ObjectInputStream(new FileInputStream(file));

            User user = (User) ois.readObject();

            System.out.println(user);


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(ois);
            try {
                FileUtils.forceDelete(file);
            } catch (IOException e) {
                //ignore
            }
        }

    }
}
