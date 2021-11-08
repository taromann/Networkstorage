package com.github.assemblathe1.client.handlers;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import com.github.assemblathe1.common.dto.DeleteRequest;
import io.netty.channel.ChannelFuture;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class ClientCommandHandler {

    private final Path watchingDirectory;
    private final int maxFrameLength;

    public ClientCommandHandler(Path watchingDirectory, int maxFrameLength) {
        this.watchingDirectory = watchingDirectory;
        this.maxFrameLength = maxFrameLength;
    }

    public void sendDirectory(ChannelFuture channelFuture, Path path) {
        AddDirectoryRequest putDirectoryRequest = new AddDirectoryRequest();
        putDirectoryRequest.setWatchingDirectory(watchingDirectory);
        putDirectoryRequest.setDirectory(path);
        System.out.println("Try to send directories from client: " + path);
        try {
            channelFuture.channel().writeAndFlush(putDirectoryRequest).sync(); //Channel передавать в метод
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(ChannelFuture channelFuture, Path path) {
        int startOffset = 0;
        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "r")) {
            byte[] buffer = new byte[maxFrameLength - 1024 * 1024];
            while (startOffset < raf.length()) {
                AddFileRequest fileToSend = new AddFileRequest();
                fileToSend.setAbsolutPath(path);
                fileToSend.setWatchingDirectory(watchingDirectory);
                raf.seek(startOffset);
                int bufferLength = raf.read(buffer);
                fileToSend.setBuffer(buffer);
                fileToSend.setBufferLength(bufferLength);
                fileToSend.setStartOffset(startOffset);
                startOffset += bufferLength;
                System.out.println("Try to send file from client: " + fileToSend.getAbsolutPath() + " " + fileToSend.getBufferLength());
                channelFuture.channel().writeAndFlush(fileToSend).sync(); //Channel передавать в метод
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(ChannelFuture channelFuture, Path path) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setAbsolutPath(path);
        deleteRequest.setWatchingDirectory(watchingDirectory);
        try {
            channelFuture.channel().writeAndFlush(deleteRequest).sync(); //Channel передавать в метод
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
