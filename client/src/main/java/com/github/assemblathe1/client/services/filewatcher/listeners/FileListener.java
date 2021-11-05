package com.github.assemblathe1.client.services.filewatcher.listeners;

import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;

import java.nio.file.WatchEvent;
import java.util.EventListener;

public interface FileListener extends EventListener {
    public void onCreated(WatchEvent.Kind<?> kind, FileEvent event);
    public void onModified(WatchEvent.Kind<?> kind, FileEvent event);
    public void onDeleted(WatchEvent.Kind<?> kind, FileEvent event);
}
