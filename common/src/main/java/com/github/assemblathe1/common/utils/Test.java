package com.github.assemblathe1.common.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {
        File directory = new File("C:\\in");
        File file = new File("C:\\in\\3.exe");

        System.out.println(directory.isFile());
        System.out.println(file.isFile());

    }
}
