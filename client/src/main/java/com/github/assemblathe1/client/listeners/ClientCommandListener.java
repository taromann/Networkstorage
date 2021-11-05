package com.github.assemblathe1.client.listeners;

import com.github.assemblathe1.common.dto.PutDirectoryRequest;
import com.github.assemblathe1.common.dto.PutFileRequest;
import com.github.assemblathe1.common.handler.ClientCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Set;

public class ClientCommandListener implements ClientCommandHandler {
    FileService fileService = new FileService();

    @Override
    public void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
    }

    @Override
    public void onPutDirectoryRequest(PutDirectoryRequest putDirectoryRequest, Set<Path> paths, Path destinationDirectory,  ChannelHandlerContext ctx) {
    }
}
