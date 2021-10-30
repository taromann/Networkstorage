package com.github.assemblathe1.common.dto;

import com.github.assemblathe1.common.handler.CommandHandler;
import lombok.Data;

import java.nio.file.Path;

@Data
public class CmdGet implements FileDTO {

    private Path absolutPath;
    private byte[] buffer;

    private Path watchingDirectory;
    private Path filename;
    private String relativePath;
    private int startOffset;
    private int bufferLength;



    @Override
    public void execute(CommandHandler commandHandler) {
        relativePath = watchingDirectory.getParent().relativize(this.absolutPath).toString();
        filename = this.absolutPath.getFileName();

        commandHandler.onCmdGet(absolutPath, buffer, watchingDirectory, filename, relativePath, startOffset, bufferLength);
    }
}
