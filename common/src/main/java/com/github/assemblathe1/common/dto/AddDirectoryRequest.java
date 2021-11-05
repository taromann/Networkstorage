package com.github.assemblathe1.common.dto;

import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.nio.file.Path;

@Data
public class AddDirectoryRequest extends FileDTO {
    private Path directory;
    private Path watchingDirectory;

    @Override
    public void execute(ChannelHandlerContext ctx, Path destinationDirectory, CommandHandler commandHandler) {
        commandHandler.onAddDirectoryRequest(this, directory, destinationDirectory, ctx);
    }
}
