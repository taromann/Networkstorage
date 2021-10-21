package com.github.assemblathe1.common.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.io.StringReader;
import java.util.List;

public class JsonDecoder extends MessageToMessageDecoder<String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        StringReader reader = new StringReader(msg);
        FileDTO file = objectMapper.readValue(reader, FileDTO.class);
        out.add(file);
    }

}
