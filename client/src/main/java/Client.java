import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        try {
            new Client().start();
        } finally {
            THREAD_POOL.shutdown();
        }

    }

    private void start() throws InterruptedException {

//        THREAD_POOL.execute(() -> {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new LineBasedFrameDecoder(256),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new JsonEncoder(),
                                    new JsonDecoder(),
                                    new FirstServerHandler());
                        }
                    });

            System.out.println("Client started");

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9000).sync();

            while (true) {

//                final String message = String.format("[%s] %s", LocalDateTime.now(), Thread.currentThread().getName());
//                channelFuture.channel().writeAndFlush(message + " :sended from client" + System.lineSeparator());
//                channelFuture.channel().writeAndFlush(message + " :sended from client" + System.lineSeparator()).sync();

                FileToSend fileToSend = new FileToSend();
                fileToSend.setPath(Path.of("\\C\123"));
                System.out.println("Try to send message from client: " + fileToSend);
                channelFuture.channel().writeAndFlush(fileToSend).sync();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
//        });
    }
}