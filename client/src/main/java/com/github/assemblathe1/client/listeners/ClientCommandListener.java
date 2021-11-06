package com.github.assemblathe1.client.listeners;

import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
import com.github.assemblathe1.common.dto.DeleteRequest;
import com.github.assemblathe1.common.handler.ClientCommandHandler;
import com.github.assemblathe1.common.utils.FileService;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

public class ClientCommandListener implements ClientCommandHandler {
    FileService fileService = new FileService();

    @Override
    public void onAddFileRequest(AddFileRequest addFileRequest, Path destinationDirectory, ChannelHandlerContext ctx) {
    }

    @Override
    public void onDeletedFileRequest(DeleteRequest deleteRequest, Path destinationDirectory, ChannelHandlerContext ctx) {

    }

    @Override
    public void onAddDirectoryRequest(AddDirectoryRequest addDirectoryRequest, Path dyrectory, Path destinationDirectory, ChannelHandlerContext ctx) {
    }
}
