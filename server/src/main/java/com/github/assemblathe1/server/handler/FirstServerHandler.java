package com.github.assemblathe1.server.handler;

import com.github.assemblathe1.common.CommandListener;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileDTO> implements CommandListener {
    private static final Object MONITOR = new Object();
    private static final Path STORAGE = Path.of("C:\\out\\");     // it is better to transfer this field to Server.java

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New active channel");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("Something were wrong: " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnected");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileDTO fileDTO) {
        try {
            Path path = Path.of(String.valueOf(STORAGE.resolve(fileDTO.getRelativePath())));
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");
            randomAccessFile.seek(fileDTO.getStartOffset());
            randomAccessFile.write(fileDTO.getBuffer(), 0, fileDTO.getBufferLength());
            System.out.println(" 1st handler: " + fileDTO.getAbsolutPath());
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            channelRead0(ctx, fileDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFiles() {

    }
}

