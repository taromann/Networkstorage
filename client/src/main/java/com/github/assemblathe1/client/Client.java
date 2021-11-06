package com.github.assemblathe1.client;

import com.github.assemblathe1.client.handlers.FirstClientHandler;
import com.github.assemblathe1.client.services.filewatcher.FileWatcher;
import com.github.assemblathe1.client.services.filewatcher.listeners.FileAdapter;
import com.github.assemblathe1.common.dto.AddDirectoryRequest;
import com.github.assemblathe1.common.dto.AddFileRequest;
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
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static final int MAX_FRAME_LENGTH = 1024 * 1024 * 8; //in common
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
    private static final Path WATCHING_DIRECTORY = Path.of("C:\\in");

    public static void main(String[] args) {
        try {
            new Client().start();
        } finally {
            THREAD_POOL.shutdown();
        }
    }

    private void start() {

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
                                    new ByteArrayEncoder(),
                                    new ByteArrayDecoder(),
                                    new JsonEncoder(),
                                    new JsonDecoder(),
                                    new FirstClientHandler());
                        }
                    });

            System.out.println("Client started");

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9000).sync();
            FileWatcher fileWatcher = new FileWatcher(WATCHING_DIRECTORY, channelFuture, new FileAdapter(this));

            fileWatcher.getSourseDirectories().forEach(directory -> sendDirectory(channelFuture, directory));
            System.out.println("On created channel future is active " + channelFuture.channel().isActive());
            fileWatcher.getSourceFiles().forEach(file -> sendFile(channelFuture, file));

            channelFuture.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void sendDirectory(ChannelFuture channelFuture, Path path) {
        AddDirectoryRequest putDirectoryRequest = new AddDirectoryRequest();
        putDirectoryRequest.setWatchingDirectory(WATCHING_DIRECTORY);
        putDirectoryRequest.setDirectory(path);
        System.out.println("Try to send directories from client: " + path);

        try {
            channelFuture.channel().writeAndFlush(putDirectoryRequest).sync(); //Channel передавать в метод
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendFile(ChannelFuture channelFuture, Path path) {
        int startOffset = 0;
        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "r")) {
            byte[] buffer = new byte[MAX_FRAME_LENGTH - 1024 * 1024];
            while (startOffset < raf.length()) {
                AddFileRequest fileToSend = new AddFileRequest();
                fileToSend.setAbsolutPath(path);
                fileToSend.setWatchingDirectory(WATCHING_DIRECTORY);
                raf.seek(startOffset);
                int bufferLength = raf.read(buffer);
                fileToSend.setBuffer(buffer);
                fileToSend.setBufferLength(bufferLength);
                fileToSend.setStartOffset(startOffset);
                startOffset += bufferLength;
                System.out.println("Try to send file from client: " + fileToSend.getAbsolutPath() + " " + fileToSend.getBufferLength());
                channelFuture.channel().writeAndFlush(fileToSend).sync(); //Channel передавать в метод
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

