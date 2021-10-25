package com.github.assemblathe1.common.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    Set<Path> directories = new HashSet<>();
    Set<Path> files = new HashSet<>();

    public Set<Path> getDirectories() {
        return directories;
    }

    public Set<Path> getFiles() {
        return files;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        files.add(file);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      directories.add(dir);
      return FileVisitResult.CONTINUE;
    }

    public void soutDirectories() {
        files.stream().map(Path::toString).forEach(System.out::println);
        directories.stream().map(Path::toString).forEach(System.out::println);


    }
}
