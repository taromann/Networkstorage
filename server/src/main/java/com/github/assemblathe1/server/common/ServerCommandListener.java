package com.github.assemblathe1.server.common;

import com.github.assemblathe1.common.dto.PutFileRequest;
import com.github.assemblathe1.common.handler.CommandHandler;
import com.github.assemblathe1.common.handler.ServerCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class ServerCommandListener implements ServerCommandHandler {

    FileService fileService = new FileService();


    @Override
    public void onPutFileRequest(PutFileRequest putFileRequest, ChannelHandlerContext ctx) {
        fileService.receiveFile(putFileRequest, ctx);
    }
}
