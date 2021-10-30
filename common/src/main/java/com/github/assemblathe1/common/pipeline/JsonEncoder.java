package com.github.assemblathe1.common.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

public class JsonEncoder extends MessageToMessageEncoder<FileDTO> {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, FileDTO file, List<Object> out) throws Exception {
        byte[] msg = objectMapper.writeValueAsBytes(file);
        out.add(msg);
    }


}