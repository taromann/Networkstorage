package com.github.assemblathe1.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.*;
import java.nio.file.Path;

@JsonAutoDetect
public class FileDTO {

    public Path path;
    public byte[] buffer;
    public String filename;
    public int startOfset = 0;

    public int getStartOfset() {
        return startOfset;
    }

    public void setStartOfset(int startOfset) {
        this.startOfset = startOfset;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public String getFilename() {
        return this.path.getFileName().toString();
    }
}
