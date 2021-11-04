package com.github.assemblathe1.common.utils;

import com.github.assemblathe1.common.dto.PutFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class FileService {

    public void sendFile() {
    }

    public void receiveFileAndWrite(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
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

    public void getFileList() {

    }




}
