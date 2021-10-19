import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FirstServerHandler extends SimpleChannelInboundHandler<FileToSend> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("New active channel");
    }

//    @Override
//    protected void channelRead0(ChannelHandlerContext /*for dispatching received msg*/ ctx, String msg) throws Exception {
//        System.out.println(msg);
//        System.out.println(" 1st handler: " + msg);
//        ctx.fireChannelRead(msg); //need to continue treatment by Second Server Handler
//        ctx.pipeline().remove(this); //need to remove this First Serever Handler
//        ctx.pipeline().addLast(new SecondServerHandler()); //add Second Server Handler
//        ctx.channel().writeAndFlush("resended from server to clinet msg: " + msg + System.lineSeparator());
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
        System.out.println("Something were wrong");
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnected");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileToSend msg) throws Exception {
        System.out.println(" 1st handler: " + msg.getPath());
    }
}

