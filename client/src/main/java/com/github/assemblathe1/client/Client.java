package com.github.assemblathe1.client;

import com.github.assemblathe1.client.handler.FirstServerHandler;
import com.github.assemblathe1.common.dto.FileDTO;
import com.github.assemblathe1.common.pipeline.JsonDecoder;
import com.github.assemblathe1.common.pipeline.JsonEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;
import java.io.RandomAccessFile;
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
                                    new LengthFieldBasedFrameDecoder(1024 * 1024 * 1024, 0, 4, 0, 4),
                                    new LengthFieldPrepender(4),
//                                    new LineBasedFrameDecoder(256),
                                    new StringEncoder(),
                                    new StringDecoder(),
                                    new JsonEncoder(),
                                    new JsonDecoder(),
                                    new FirstServerHandler());
                        }
                    });

            System.out.println("Client started");

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9000).sync();

//                final String message = String.format("[%s] %s", LocalDateTime.now(), Thread.currentThread().getName());
//                channelFuture.channel().writeAndFlush(message + " :sended from client" + System.lineSeparator());
//                channelFuture.channel().writeAndFlush(message + " :sended from client" + System.lineSeparator()).sync();

            getFileToSend(channelFuture, Path.of("C:/in/in.txt"));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
//        });
    }

    private void getFileToSend(ChannelFuture channelFuture, Path path) {

        long startOffset = 0;

        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "r")) {

            while (startOffset < raf.length()) {
                FileDTO fileToSend = new FileDTO();
                fileToSend.setPath(path);

                byte[] buffer = new byte[5];
                raf.seek(startOffset);
                int bufferLength = raf.read(buffer);
                fileToSend.setBuffer(buffer);
                fileToSend.setLength(bufferLength);
                fileToSend.setStartOffset(startOffset);
                startOffset += 5;
                System.out.println("Try to send message from client: " + fileToSend.getPath());
                channelFuture.channel().writeAndFlush(fileToSend).sync(); //Channel передавать в метод
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //        fileToSend.setBuffer(FileByteReader.readBytesFromFile(path));
    }
}

