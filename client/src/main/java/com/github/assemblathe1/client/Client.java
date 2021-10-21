package com.github.assemblathe1.client;

import com.github.assemblathe1.client.handler.FirstServerHandler;
import com.github.assemblathe1.common.dto.FileDTO;
import com.github.assemblathe1.common.pipeline.JsonDecoder;
import com.github.assemblathe1.common.pipeline.JsonEncoder;
import com.github.assemblathe1.common.utils.FileByteReader;
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

import java.io.FileNotFoundException;
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

        int startOfset = 0;


        for (int i = 0; i < 5; i++) {
            FileDTO fileToSend = new FileDTO();
            fileToSend.setPath(path);
            try {
                byte[] buffer = new byte[5];
                RandomAccessFile randomAccessFile = new RandomAccessFile(String.valueOf(path), "r");
                randomAccessFile.seek(startOfset);
                randomAccessFile.read(buffer, 0, 5);
                fileToSend.setBuffer(buffer);
                startOfset += 5;
            } catch (IOException e) {
                e.printStackTrace();
            }
            //        fileToSend.setBuffer(FileByteReader.readBytesFromFile(path));
            try {
                System.out.println("Try to send message from client: " + fileToSend.getPath());
                channelFuture.channel().writeAndFlush(fileToSend).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
