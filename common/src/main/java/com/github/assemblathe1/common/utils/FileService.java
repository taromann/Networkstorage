package com.github.assemblathe1.common.utils;

import com.github.assemblathe1.common.dto.PutFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class FileService {

    private static final Path STORAGE = Path.of("C:\\out\\");     // it is better to transfer this field to Server.java

    public void sendFile() {

    }

    public void receiveFile(PutFileRequest putFileRequest, ChannelHandlerContext ctx) {
        try {
            Path path = Path.of(String.valueOf(STORAGE.resolve(putFileRequest.getRelativePath())));
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");
            randomAccessFile.seek(putFileRequest.getStartOffset());
            randomAccessFile.write(putFileRequest.getBuffer(), 0, putFileRequest.getBufferLength());
            System.out.println(" 1st handler: " + putFileRequest.getAbsolutPath());
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            channelRead0(ctx, fileDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getFileList() {

    }




}
