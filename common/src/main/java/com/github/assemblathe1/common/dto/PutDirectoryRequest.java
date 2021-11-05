package com.github.assemblathe1.common.dto;

import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Data
public class PutDirectoryRequest extends FileDTO {
    private Path directory;
    private Path watchingDirectory;

    @Override
    public void execute(ChannelHandlerContext ctx, Path destinationDirectory, CommandHandler commandHandler) {
        commandHandler.onPutDirectoryRequest(this, directory, destinationDirectory, ctx);
    }
}
