package com.github.assemblathe1.client.services.filewatcher;

import com.github.assemblathe1.client.services.filewatcher.listeners.FileListener;
import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;
import io.netty.channel.ChannelFuture;
import lombok.Data;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.*;

@Data
public class FileWatcher extends SimpleFileVisitor<Path> {

    protected List<FileListener> listeners = new ArrayList<>();
    public Map<Path, WatchService> runningWatchServices = new HashMap<>();
    private Set<Path> sourseDirectories = new HashSet<>();
    private Set<Path> sourceFiles = new HashSet<>();
    ChannelFuture channelFuture;

    public FileWatcher(Path folder, ChannelFuture channelFuture, FileListener listener) {
        this.channelFuture = channelFuture;
        createFoldersTree(folder);
        listeners.add(listener);
    }

    private void createFoldersTree(Path folder) {
        try {
            Files.walkFileTree(folder, this);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        sourceFiles.add(file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
        addDirectoryToFileWatcher(path);
        sourseDirectories.add(path);
        return FileVisitResult.CONTINUE;
    }

    public void addDirectoryToFileWatcher(Path path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Started watching path: " + path + " in thread: " + Thread.currentThread().getName());
                try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                    path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
                    runningWatchServices.put(path, watchService);
                    boolean poll = true;
                    while (poll) {
                        poll = pollEvents(watchService);
                    }
                } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException, IOException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for (WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(watchService, event.kind(), path.resolve((Path) event.context()));
        }
        return key.reset();
    }

    protected void notifyListeners(WatchService watchService, WatchEvent.Kind<?> kind, Path path) {
        FileEvent event = new FileEvent(path);
        if (kind == ENTRY_CREATE) {
            for (FileListener listener : listeners) {
                listener.onCreated(channelFuture, kind, event);
            }
            if (path.toFile().isDirectory()) {
                addDirectoryToFileWatcher(path);
            }
        } else if (kind == ENTRY_MODIFY) {
            for (FileListener listener : listeners) {
                listener.onModified(kind, event);
            }
        } else if (kind == ENTRY_DELETE) {
            for (FileListener listener : listeners) {
                listener.onDeleted(kind, event);
            }
            try {
                if (path.toFile().isDirectory()) {
                    runningWatchServices.get(path).close();
                    runningWatchServices.remove(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
