package com.github.assemblathe1.common.handler;

import com.github.assemblathe1.common.dto.PutDirectoryRequest;
import com.github.assemblathe1.common.dto.PutFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Set;

public interface CommandHandler {
    void onPutFileRequest(PutFileRequest putFileRequest, Path destinationDirectory, ChannelHandlerContext ctx);
    void onPutDirectoryRequest(PutDirectoryRequest putDirectoryRequest, Path path, Path destinationDirectory, ChannelHandlerContext ctx);
}
