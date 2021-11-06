package com.github.assemblathe1.client.services.filewatcher.listeners;

import com.github.assemblathe1.client.Client;
import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;
import io.netty.channel.ChannelFuture;

import java.nio.file.WatchEvent;

public class FileAdapter implements FileListener {

    Client client;

    public FileAdapter(Client client) {
        this.client = client;
    }

    @Override
    public void onCreated(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event) {
        System.out.println(kind + " " + event.getFile() + "  " + event);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (event.getFile().toFile().isFile()) {
            client.sendFile(channelFuture, event.getFile());
        } else if (event.getFile().toFile().isDirectory()) {
            client.sendDirectory(channelFuture, event.getFile());
        }


    }
    @Override
    public void onModified(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event) {
        if (event.getFile().toFile().isFile()) {
            System.out.println(kind + " " + event.getFile());
            client.sendFile(channelFuture, event.getFile());
        } else if (event.getFile().toFile().isDirectory()) {
//            client.sendDirectory(channelFuture, event.getFile());
        }
    }

    @Override
    public void onDeleted(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event) {
        System.out.println(kind + " " + event.getFile());
        client.deleteFile(channelFuture, event.getFile());
    }
}
