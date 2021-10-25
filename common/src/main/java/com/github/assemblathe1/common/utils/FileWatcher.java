package com.github.assemblathe1.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class FileWatcher {

    private Set<Path> sourseDirectories;
    private Set<Path> files;
    private final Path sourseDirectory;
    private final Path destinationDirectory;

    public Set<Path> getFiles() {
        return files;
    }

    public FileWatcher(Path sourseDirectory, Path destinationDirectory) {
        this.sourseDirectory = sourseDirectory;
        this.destinationDirectory = destinationDirectory;

        MyFileVisitor myFileVisitor = new MyFileVisitor();
        sourseDirectories = myFileVisitor.getDirectories();
        files = myFileVisitor.getFiles();
        try {
            Files.walkFileTree(sourseDirectory, myFileVisitor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            myFileVisitor.soutDirectories();
        }

        copyFileTree(sourseDirectories);
    }

    private void copyFileTree(Set<Path> sourseDirectories) {
        sourseDirectories.stream().map(path -> sourseDirectory.getParent().relativize(path)).map(destinationDirectory::resolve).forEach(path -> {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(path);
        });
    }





}
