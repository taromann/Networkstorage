package com.github.assemblathe1.common.dto;

import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

import java.nio.file.Path;

@Data
public class AddFileRequest extends FileDTO {

    private byte[] buffer;
    private int startOffset;
    private int bufferLength;

    private Path watchingDirectory;
    private Path absolutPath;
    private String relativePath;


    @Override
    public void execute(ChannelHandlerContext ctx, Path destinationDirectory, CommandHandler commandHandler) {
        relativePath = watchingDirectory.getParent().relativize(this.absolutPath).toString();
        commandHandler.onAddFileRequest(this, destinationDirectory, ctx);
    }
}
