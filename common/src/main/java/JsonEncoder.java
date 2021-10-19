import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.StringWriter;
import java.util.List;

public class JsonEncoder extends MessageToMessageEncoder<FileToSend> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FileToSend file, List<Object> out) throws Exception {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, file);
//        System.out.println(writer.toString());
        out.add(writer.toString() +"\n");
    }

}