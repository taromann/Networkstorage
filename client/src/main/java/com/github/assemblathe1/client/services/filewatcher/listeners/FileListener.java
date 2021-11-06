package com.github.assemblathe1.client.services.filewatcher.listeners;

import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;
import io.netty.channel.ChannelFuture;

import java.nio.file.WatchEvent;
import java.util.EventListener;

public interface FileListener extends EventListener {
    public void onCreated(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event);
    public void onModified(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event);
    public void onDeleted(ChannelFuture channelFuture, WatchEvent.Kind<?> kind, FileEvent event);
}
