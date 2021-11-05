package com.github.assemblathe1.server.listeners;

import com.github.assemblathe1.common.dto.PutDirectoryRequest;
import com.github.assemblathe1.common.dto.PutFileRequest;
import com.github.assemblathe1.common.handler.ServerCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Set;

public class ServerCommandListener implements ServerCommandHandler {

    FileService fileService = new FileService();

    @Override
    public void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.onPutFileRequest(putFileRequest, destinationDirectory,  ctx);
    }

    @Override
    public void onPutDirectoryRequest(PutDirectoryRequest putDirectoryRequest, Set<Path> paths, Path destinationDirectory,  ChannelHandlerContext ctx) {
        fileService.onPutDirectoryRequest(putDirectoryRequest, paths, destinationDirectory, ctx);
    }
}
