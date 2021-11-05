package com.github.assemblathe1.common.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.assemblathe1.common.handler.CommandHandler;
import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PutDirectoryRequest.class, name = "putDirectoryRequest"),
        @JsonSubTypes.Type(value = PutFileRequest.class, name = "putFileRequest")
})


public abstract class FileDTO {

    public abstract void execute(ChannelHandlerContext ctx, Path destinationDirectory, CommandHandler commandHandler);

}
