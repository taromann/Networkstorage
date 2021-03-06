package com.github.assemblathe1.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class FileByteWriter {
    public static void writeBytesToFile(Path path, byte[] bytes) {
        try {
            FileOutputStream fos = new FileOutputStream(String.valueOf(path));
            fos.write(bytes, 0, bytes.length);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
