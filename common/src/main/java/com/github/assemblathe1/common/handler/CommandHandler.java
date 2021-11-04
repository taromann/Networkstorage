package com.github.assemblathe1.common.handler;

import com.github.assemblathe1.common.dto.PutFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

public interface CommandHandler {

    void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx);
}
