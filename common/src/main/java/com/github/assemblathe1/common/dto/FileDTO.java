package com.github.assemblathe1.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.nio.file.Path;

@JsonAutoDetect
public class FileDTO {

    private Path path;
    private byte[] buffer;
    private String filename;
    private long startOffset;
    private int length;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
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
