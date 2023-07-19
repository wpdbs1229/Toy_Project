package com.example.user.config.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class ReadFile {

    public static String readFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String key = reader.readLine();
            return key;
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
