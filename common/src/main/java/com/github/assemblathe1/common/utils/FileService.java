package com.github.assemblathe1.common.utils;

import com.github.assemblathe1.common.dto.PutDirectoryRequest;
import com.github.assemblathe1.common.dto.PutFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class FileService {

    public void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        try {
            Path path = Path.of(String.valueOf(destinationDirectory.resolve(putFileRequest.getRelativePath())));
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");
            randomAccessFile.seek(putFileRequest.getStartOffset());
            randomAccessFile.write(putFileRequest.getBuffer(), 0, putFileRequest.getBufferLength());
            System.out.println(" 1st handler: " + putFileRequest.getAbsolutPath());
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPutDirectoryRequest(PutDirectoryRequest putDirectoryRequest, Path paths, Path destinationDirectory, ChannelHandlerContext ctx) {

        try {
            Files.createDirectories(destinationDirectory.resolve(putDirectoryRequest.getWatchingDirectory().getParent().relativize(paths)));
        } catch (IOException e) {
            e.printStackTrace();
        }


//        paths.stream().map(path -> putDirectoryRequest.getWatchingDirectory().getParent().relativize(path)).map(destinationDirectory::resolve).forEach(path -> {
//            try {
//                Files.createDirectories(path);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        paths.stream().map(path -> putDirectoryRequest.getWatchingDirectory().getParent().relativize(path)).map(destinationDirectory::resolve).forEach(System.out::println);
    }
}
