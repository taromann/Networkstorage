package com.github.assemblathe1.common.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
//        @JsonSubTypes.Type(value = CmdPut.class, name = "cmdPut"),
        @JsonSubTypes.Type(value = PutFileRequest.class, name = "cmdGet")
})


public abstract class FileDTO {

    public abstract void execute(ChannelHandlerContext ctx, CommandHandler commandHandler);


//    private Path absolutPath;
//    private byte[] buffer;
//
//    private Path watchingDirectory;
//    private Path filename;
//    private String relativePath;
//    private int startOffset;
//    private int bufferLength;
//
//    public FileDTO() {
//    }
//
//    public Path getWatchingDirectory() {
//        return watchingDirectory;
//    }
//
//    public void setWatchingDirectory(Path watchingDirectory) {
//        this.watchingDirectory = watchingDirectory;
//        this.relativePath = watchingDirectory.getParent().relativize(this.absolutPath).toString();
//    }
//
//    public void setFilename(String filename) {
//        this.filename = this.absolutPath.getFileName();
//    }
//
//    public String getRelativePath() {
//        return relativePath;
//    }
//
//    public int getBufferLength() {
//        return bufferLength;
//    }
//
//    public void setBufferLength(int bufferLength) {
//        this.bufferLength = bufferLength;
//    }
//
//    public int getStartOffset() {
//        return startOffset;
//    }
//
//    public void setStartOffset(int startOffset) {
//        this.startOffset = startOffset;
//    }
//
//    public void setAbsolutPath(Path absolutPath) {
//        this.absolutPath = absolutPath;
//    }
//
//    public Path getAbsolutPath() {
//        return absolutPath;
//    }
//
//    public byte[] getBuffer() {
//        return buffer;
//    }
//
//    public void setBuffer(byte[] buffer) {
//        this.buffer = buffer;
//    }
//
//    public String getFilename() {
//        return this.absolutPath.getFileName().toString();
//    }
}
