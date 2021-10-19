import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@JsonAutoDetect
public class FileToSend {

    public Path path;
    public byte[] buffer;

//    public int getBuffer() {
//        return buffer.length;
//    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
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

    public void writeFileByByres() {
        try {
            FileOutputStream fos = new FileOutputStream(String.valueOf("C:\\out\\out.txt"));
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
