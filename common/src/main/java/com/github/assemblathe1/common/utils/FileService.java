package com.github.assemblathe1.common.utils;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    public void onAddFileRequest(AddFileRequest addFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        try {
            Path path = Path.of(String.valueOf(destinationDirectory.resolve(addFileRequest.getRelativePath())));
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");
            randomAccessFile.seek(addFileRequest.getStartOffset());
            randomAccessFile.write(addFileRequest.getBuffer(), 0, addFileRequest.getBufferLength());
            System.out.println(" 1st handler: " + addFileRequest.getAbsolutPath());
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddDirectoryRequest(AddDirectoryRequest addDirectoryRequest, Path paths, Path destinationDirectory, ChannelHandlerContext ctx) {

        try {
            Files.createDirectories(destinationDirectory.resolve(addDirectoryRequest.getWatchingDirectory().getParent().relativize(paths)));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        paths.stream().map(path -> addDirectoryRequest.getWatchingDirectory().getParent().relativize(path)).map(destinationDirectory::resolve).forEach(path -> {
//            try {
//                Files.createDirectories(path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        paths.stream().map(path -> addDirectoryRequest.getWatchingDirectory().getParent().relativize(path)).map(destinationDirectory::resolve).forEach(System.out::println);
    }
}
