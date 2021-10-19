import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FirstServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New active channel");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext /*for dispatching received msg*/ ctx, String msg) throws Exception {
        System.out.println(msg);
//        ctx.channel().writeAndFlush("receive msg: " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        System.out.println("Something were wrong");
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnected");
        System.out.println(ctx);
    }
}
