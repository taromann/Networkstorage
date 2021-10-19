import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.*;
import java.nio.file.Path;

@JsonAutoDetect
public class FileToSend {

    public Path path;
    public byte[] buffer;
    String filename;

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public String getFilename() {
        return this.path.getFileName().toString();
    }

    public void readFileToBytes() {
        try {
            FileInputStream fin = new FileInputStream(String.valueOf(path));
            this.buffer = new byte[fin.available()];
            fin.read(this.buffer, 0, this.buffer.length);
            System.out.println(this.buffer.length);
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFileByByres(String path) {
        try {
            FileOutputStream fos = new FileOutputStream(String.valueOf(path));
            fos.write(this.buffer, 0, this.buffer.length);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    FileToSend() {
    }

}
