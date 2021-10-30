package com.github.assemblathe1.common.utils;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
//        File directory = new File("C:\\in");
//        File file = new File("C:\\in\\3.exe");
//
//        System.out.println(directory.isFile());
//        System.out.println(file.isFile());
//
//        Path path = Path.of("C:\\in\\3.exe");
//        Path path1 = Paths.get("C:\\in\\3.exe");

//        System.out.println(path);
//        System.out.println(path1);

        List<String> strings = List.of("1", "2");
        strings.add("2");
        strings.stream().forEach(System.out::println);


    }


}
