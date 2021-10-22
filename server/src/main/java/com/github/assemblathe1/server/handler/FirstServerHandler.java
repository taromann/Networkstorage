package com.github.assemblathe1.server.handler;

import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.RandomAccessFile;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileDTO> {

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
    protected void channelRead0(ChannelHandlerContext ctx, FileDTO fileDTO) throws Exception {
//        FileByteWriter.writeBytesToFile(Path.of("C:\\out\\" + fileDTO.getFilename()), fileDTO.getBuffer());
//        RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\out\\in.txt", "rw");
//        randomAccessFile.write(fileDTO.getBuffer(), fileDTO.startOfset, 5);

        System.out.println(" 1st handler: " + fileDTO.getPath());
    }
}

