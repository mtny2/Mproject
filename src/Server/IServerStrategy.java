package Server;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Server strategy Interface class
 */
public interface IServerStrategy {
    void serverStrategy(InputStream inputStream, OutputStream outputStream);
}
