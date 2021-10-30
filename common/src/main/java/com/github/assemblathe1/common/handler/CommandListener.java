package com.github.assemblathe1.common.handler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class CommandListener implements CommandHandler {

    private static final Path STORAGE = Path.of("C:\\out\\");     // it is better to transfer this field to Server.java


    @Override
    public void onCmdGet(Path absolutPath, byte[] buffer, Path watchingDirectory, Path filename, String relativePath, int startOffset, int bufferLength) {
        try {
            Path path = Path.of(String.valueOf(STORAGE.resolve(relativePath)));
            RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "rw");
            randomAccessFile.seek(startOffset);
            randomAccessFile.write(buffer, 0, bufferLength);
            System.out.println(" 1st handler: " + absolutPath);
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            channelRead0(ctx, fileDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
