package com.github.assemblathe1.common.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.nio.file.Path;

@JsonAutoDetect
public class FileDTO {

    private Path path;
    private byte[] buffer;
    private Path filename;
    private Path relativePath;
    private int startOffset;
    private int bufferLength;

    public FileDTO(Path path) {
        this.path = path;

    }

    public void setFilename(String filename) {
        this.filename = this.path.getFileName();
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public int getBufferLength() {
        return bufferLength;
    }

    public void setBufferLength(int bufferLength) {
        this.bufferLength = bufferLength;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
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
