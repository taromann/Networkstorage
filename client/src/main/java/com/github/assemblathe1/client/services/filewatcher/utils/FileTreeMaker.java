package com.github.assemblathe1.client.services.filewatcher.utils;

import lombok.Data;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

@Data
public class FileTreeMaker extends SimpleFileVisitor<Path> {

    private Set<Path> sourseDirectories = new HashSet<>();
    private Set<Path> sourceFiles = new HashSet<>();

    public FileTreeMaker(Path sourseDirectories) {
        createFoldersTree(sourseDirectories);
    }

    private void createFoldersTree(Path folder) {
        try {
            Files.walkFileTree(folder, this);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        sourceFiles.add(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
        sourseDirectories.add(path);
        return FileVisitResult.CONTINUE;
    }

}
