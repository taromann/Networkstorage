package com.github.assemblathe1.server.handler;

import com.github.assemblathe1.server.common.ServerCommandListener;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Path;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileDTO> {

    private final Path destinationDirectory;

    public FirstServerHandler(Path destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

      // it is better to transfer this field to Server.java

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
        fileDTO.execute(ctx, destinationDirectory, new ServerCommandListener());
    }


}

