package com.github.assemblathe1.common.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.StringWriter;
import java.util.List;

public class JsonEncoder extends MessageToMessageEncoder<FileDTO> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileDTO file, List<Object> out) throws Exception {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, file);
//        System.out.println(writer.toString());
        out.add(writer.toString());
    }

}