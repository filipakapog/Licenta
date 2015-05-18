package licenta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.*;

/**
 * Created by filip on 16.05.2015.
 */

/**
 * Represents a set of helper features used in the application. This class is static and thus cannot
 * be instantiated.
 */
public class Util {

    private static final String chromeHistoryDBPath =
            "/home/filip/.config/google-chrome/Default/History";
    private static final String userHistoryDBPath = "userHistory.db";

    private Util() {}

    public static String getUserHistoryDBPath() {
        return userHistoryDBPath;
    }

    /**
     * Initializes the DB with which the application works by copying Chrome's History DB into
     * userHistory.db file. This is done due to the fact than Chrome's History DB cannot be accessed
     * directly, so we need to provide a workaround to access it.
     */
    public static void initHistoryDBPath() throws IOException {
        Path source = Paths.get(chromeHistoryDBPath);
        Path destination = Paths.get(userHistoryDBPath);
        Files.copy(source, destination, REPLACE_EXISTING);
    }

    /**
     * Removes the userHistory.db file, because we always use a fresh copy of Chrome's History DB.
     */
    public  static void deleteHistoryDBPath() throws IOException {
        Path path = Paths.get(userHistoryDBPath);
        Files.delete(path);
    }
}
