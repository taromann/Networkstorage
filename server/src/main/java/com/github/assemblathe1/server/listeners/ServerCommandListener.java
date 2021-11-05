package com.github.assemblathe1.server.listeners;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import com.github.assemblathe1.common.handler.ServerCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

public class ServerCommandListener implements ServerCommandHandler {

    FileService fileService = new FileService();

    @Override
    public void onAddFileRequest(AddFileRequest addFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onAddFileRequest(addFileRequest, destinationDirectory,  ctx);
    }

    @Override
    public void onAddDirectoryRequest(AddDirectoryRequest addDirectoryRequest, Path directory, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onAddDirectoryRequest(addDirectoryRequest, directory, destinationDirectory, ctx);
    }
}