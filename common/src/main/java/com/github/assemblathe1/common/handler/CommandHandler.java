package com.github.assemblathe1.common.handler;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

public interface CommandHandler {
    void onAddFileRequest(AddFileRequest addFileRequest, Path destinationDirectory, ChannelHandlerContext ctx);
    void onAddDirectoryRequest(AddDirectoryRequest addDirectoryRequest, Path path, Path destinationDirectory, ChannelHandlerContext ctx);
}
