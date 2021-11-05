package com.github.assemblathe1.client.services.filewatcher.utils;

import java.nio.file.Path;
import java.util.EventObject;

public class FileEvent extends EventObject {

    public FileEvent(Path path) {
        super(path);
    }

    public Path getFile() {
        return (Path) getSource();
    }


}
