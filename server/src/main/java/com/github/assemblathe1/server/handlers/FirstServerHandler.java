package com.github.assemblathe1.server.handlers;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import com.github.assemblathe1.common.dto.DeleteRequest;
import com.github.assemblathe1.common.handler.ServerCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.file.Path;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileDTO> implements ServerCommandHandler {

    private final Path destinationDirectory;
    FileService fileService = new FileService();

    public FirstServerHandler(Path destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    @Override
    public void onAddFileRequest(AddFileRequest addFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onAddFileRequest(addFileRequest, destinationDirectory,  ctx);
    }

    @Override
    public void onDeletedFileRequest(DeleteRequest deleteRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onDeleteFileRequest(deleteRequest, destinationDirectory,  ctx);
    }

    @Override
    public void onAddDirectoryRequest(AddDirectoryRequest addDirectoryRequest, Path directory, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onAddDirectoryRequest(addDirectoryRequest, directory, destinationDirectory, ctx);
    }


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
        fileDTO.execute(ctx, destinationDirectory, this);
    }


}

