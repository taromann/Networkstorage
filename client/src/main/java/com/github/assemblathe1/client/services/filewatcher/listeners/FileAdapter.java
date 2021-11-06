package com.github.assemblathe1.client.services.filewatcher.listeners;

import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;

import java.nio.file.WatchEvent;

public class FileAdapter implements FileListener {

    @Override
    public void onCreated(WatchEvent.Kind<?> kind, FileEvent event) {
        System.out.println(kind + " " + event.getFile() + "  " + event);
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
