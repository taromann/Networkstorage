package com.github.assemblathe1.server.common;

import com.github.assemblathe1.common.dto.PutFileRequest;
import com.github.assemblathe1.common.handler.ServerCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

public class ServerCommandListener implements ServerCommandHandler {

    FileService fileService = new FileService();



    @Override
    public void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
        fileService.receiveFileAndWrite(putFileRequest, destinationDirectory,  ctx);
    }
}
