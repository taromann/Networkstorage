package com.github.assemblathe1.common.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.StringReader;
import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<byte[]> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        FileDTO file = objectMapper.readValue(msg, FileDTO.class);
        out.add(file);
    }

}
