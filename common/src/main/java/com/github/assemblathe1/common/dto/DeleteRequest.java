package com.github.assemblathe1.common.dto;

import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.nio.file.Path;

@Data
public class DeleteRequest extends FileDTO {

    private Path watchingDirectory;
    private Path absolutPath;
    private Path relativePath;


    @Override
    public void execute(ChannelHandlerContext ctx, Path destinationDirectory, CommandHandler commandHandler) {
        relativePath = watchingDirectory.getParent().relativize(this.absolutPath);
        commandHandler.onDeletedFileRequest(this, destinationDirectory, ctx);
    }

}
