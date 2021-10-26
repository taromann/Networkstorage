package com.github.assemblathe1.server.handler;

import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileDTO> {
    private static final Object MONITOR = new Object();
    private static final Path STORAGE = Path.of("C:\\out\\");     // it is better to transfer this field to Server.java

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New active channel");
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext /*for dispatching received msg*/ ctx, String msg) throws Exception {
//        System.out.println(msg);
//        System.out.println(" 1st handler: " + msg);
//        ctx.fireChannelRead(msg); //need to continue treatment by Second com.github.assemblathe1.server.Server Handler
//        ctx.pipeline().remove(this); //need to remove this First Serever Handler
//        ctx.pipeline().addLast(new com.github.assemblathe1.server.handler.SecondServerHandler()); //add Second com.github.assemblathe1.server.Server Handler
//        ctx.channel().writeAndFlush("resended from server to clinet msg: " + msg + System.lineSeparator());
//    }

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
//        FileByteWriter.writeBytesToFile(Path.of("C:\\out\\" + fileDTO.getFilename()), fileDTO.getBuffer());
        try {
            Path path = Path.of(String.valueOf(STORAGE.resolve(fileDTO.getRelativePath())));
//            Path path = Path.of(STORAGE.toString() + fileDTO.getRelativePath());
            System.out.println("path = " + path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");

//            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(STORAGE.toString() + fileDTO.getRelativePath()), "rw");
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
}

