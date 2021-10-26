package com.github.assemblathe1.common.pipeline;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.assemblathe1.common.dto.FileDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.List;

public class JsonEncoder extends MessageToMessageEncoder<FileDTO> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileDTO file, List<Object> out) throws Exception {
        ByteBuf byteBuf = new
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(byteArrayOutputStream, file);
//        System.out.println(writer.toString());
        out.add(byteArrayOutputStream);
    }

}