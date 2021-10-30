package com.github.assemblathe1.common.handler;

import java.nio.file.Path;

public interface CommandHandler {

    void onCmdGet(Path absolutPath, byte[] buffer, Path watchingDirectory, Path filename, String relativePath, int startOffset, int bufferLength);

}
