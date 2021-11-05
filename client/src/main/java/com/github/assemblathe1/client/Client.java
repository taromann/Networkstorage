package com.github.assemblathe1.client;

import com.github.assemblathe1.client.handlers.FirstClientHandler;
import com.github.assemblathe1.common.dto.PutDirectoryRequest;
import com.github.assemblathe1.common.dto.PutFileRequest;
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
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static final int MAX_FRAME_LENGTH = 1024 * 1024 * 8; //in common
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);
    private static final Path WATCHING_DIRECTORY = Path.of("C:\\in");

    public static void main(String[] args) throws InterruptedException {
        try {
            new Client().start();
        } finally {
            THREAD_POOL.shutdown();
        }

    }

    private void start() throws InterruptedException {

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
            FileWatcher fileWatcher = new FileWatcher(WATCHING_DIRECTORY);
            sendDirectories(channelFuture, fileWatcher.getSourseDirectories());
            fileWatcher.getSourceFiles().forEach(path -> /*System.out.println(path) */ sendFile(channelFuture, path));

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
    }

    private void sendDirectories(ChannelFuture channelFuture, Set<Path> paths) {
        PutDirectoryRequest putDirectoryRequest = new PutDirectoryRequest();
        putDirectoryRequest.setWatchingDirectory(WATCHING_DIRECTORY);
        putDirectoryRequest.setDirectoriesStructore(paths);
        System.out.println("Try to send directories from client: ");
        paths.forEach(System.out::println);
        try {
            channelFuture.channel().writeAndFlush(putDirectoryRequest).sync(); //Channel передавать в метод
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(ChannelFuture channelFuture, Path path) {
        int startOffset = 0;
        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "r")) {
            byte[] buffer = new byte[MAX_FRAME_LENGTH - 1024 * 1024];
            while (startOffset < raf.length()) {
                PutFileRequest fileToSend = new PutFileRequest();
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }





}

