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
        System.out.println("On created channel future is active " + channelFuture.channel().isActive());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.sendFile(channelFuture, event.getFile());
    }
    @Override
    public void onModified(WatchEvent.Kind<?> kind, FileEvent event) {
//        System.out.println(kind + " " + event.getFile());
    }

    @Override
    public void onDeleted(WatchEvent.Kind<?> kind, FileEvent event) {
        System.out.println(kind + " " + event.getFile());
    }
}
