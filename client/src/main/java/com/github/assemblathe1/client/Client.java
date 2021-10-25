package com.github.assemblathe1.client;

import com.github.assemblathe1.client.handler.FirstServerHandler;
import com.github.assemblathe1.common.dto.FileDTO;
import com.github.assemblathe1.common.pipeline.JsonDecoder;
import com.github.assemblathe1.common.pipeline.JsonEncoder;
import com.github.assemblathe1.common.utils.FileWatcher;
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
    public static final int MAX_FRAME_LENGTH = 1024 * 1024 * 8; //in common
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
    private static final Path SOURCE_DIRECTORY = Path.of("C:\\in");
    private static final Path DESTINATION_DIRECTORY = Path.of("C:\\out");

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
                                    new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, 4, 0, 4),
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
            FileWatcher fileWatcher = new FileWatcher(SOURCE_DIRECTORY, DESTINATION_DIRECTORY);
            fileWatcher.getFiles().stream().forEach(path -> /*System.out.println(path) */ sendFile(channelFuture, path));

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

    private void sendFile(ChannelFuture channelFuture, Path path) {
        int startOffset = 0;
        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "r")) {
            byte[] buffer = new byte[MAX_FRAME_LENGTH - 1024 * 1024];
            while (startOffset < raf.length()) {
                FileDTO fileToSend = new FileDTO();
                fileToSend.setPath(path);
                raf.seek(startOffset);
                int bufferLength = raf.read(buffer);
                fileToSend.setBuffer(buffer);
                fileToSend.setBufferLength(bufferLength);
                fileToSend.setStartOffset(startOffset);
                startOffset += bufferLength;
                System.out.println("Try to send message from client: " + fileToSend.getPath() + " " + fileToSend.getBufferLength());
                channelFuture.channel().writeAndFlush(fileToSend).sync(); //Channel передавать в метод
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }





}

