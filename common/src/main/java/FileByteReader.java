import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class FileByteReader {
    private static byte[] buffer;

    public static byte[] readBytesFromFile(Path path) {
        try {
            FileInputStream fin = new FileInputStream(String.valueOf(path));
            buffer = new byte[fin.available()];
            fin.read(buffer, 0, buffer.length);
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
