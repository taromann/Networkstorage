package com.github.assemblathe1.client.services.filewatcher;

import com.github.assemblathe1.client.Client;
import com.github.assemblathe1.client.handlers.ClientCommandHandler;
import com.github.assemblathe1.client.services.filewatcher.listeners.FileListener;
import com.github.assemblathe1.client.services.filewatcher.utils.FileEvent;
import com.github.assemblathe1.client.services.filewatcher.utils.FileTreeMaker;
import io.netty.channel.ChannelFuture;
import lombok.Data;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

@Data
public class FileWatcher {

    protected List<FileListener> listeners = new ArrayList<>();
    private Map<Path, WatchService> runningWatchServices = new HashMap<>();
    private Path watchingDirecory;
    private ChannelFuture channelFuture;
    private Client client;
    private FileListener fileListener;
    private int maxFrameLength;

    public FileWatcher(Path watchingDirecory, ChannelFuture channelFuture, FileListener listener, int maxFrameLength) {
        this.channelFuture = channelFuture;
        this.watchingDirecory = watchingDirecory;
        this.fileListener = listener;
        this.maxFrameLength = maxFrameLength;
    }

    public void startWatching() {
        listeners.add(fileListener);
        ClientCommandHandler clientCommandHandler = new ClientCommandHandler(watchingDirecory, maxFrameLength);
        FileTreeMaker fileTreeMaker = new FileTreeMaker(watchingDirecory);
        fileTreeMaker.getSourseDirectories().forEach(path -> clientCommandHandler.sendDirectory(channelFuture, path));
        fileTreeMaker.getSourceFiles().forEach(path -> clientCommandHandler.sendFile(channelFuture, path));
        fileTreeMaker.getSourseDirectories().forEach(this::addDirectoryToFileWatcher);
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
                listener.onModified(channelFuture, kind, event);
            }
        } else if (kind == ENTRY_DELETE) {
            for (FileListener listener : listeners) {
                listener.onDeleted(channelFuture, kind, event);
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
