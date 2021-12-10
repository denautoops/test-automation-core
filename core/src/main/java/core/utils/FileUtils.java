package core.utils;

import core.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static core.constants.Global.EMPTY_STRING;

/**
 * Utils for work with files
 * <p>
 *
 * @author Denis.Martynov
 * Created on 29.03.21
 */
public final class FileUtils {

    private static final Logger LOGGER = Logger.getInstance(FileUtils.class);

    private FileUtils() {
    }

    public static File getFileFromResources(String relativeFilePath) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();

        URL resource = classLoader.getResource(relativeFilePath);
        if (resource == null) {
            throw new AssertionError("File '" + relativeFilePath + "' is not found!");
        } else {
            return new File(resource.getFile());
        }
    }

    public static File createTmpFile(String fileName, String ext) throws IOException {
        Path tmpPath = Path.of(Paths.get(EMPTY_STRING).toAbsolutePath() + "/tmp");
        try {
            Files.createDirectory(tmpPath);
        } catch (FileAlreadyExistsException ignore) {
        }

        return File.createTempFile(fileName, ext, new File(tmpPath + EMPTY_STRING));
    }

    public static byte[] getImageFromFile(String imageFilePath) {
        byte[] byteArray = null;
        try {
            File file = new File(imageFilePath);
            byteArray = org.apache.commons.io.FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }
        return byteArray;
    }


    public static byte[] getImageFromFile(File file) {
        byte[] byteArray = null;
        try {
            byteArray = org.apache.commons.io.FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }
        return byteArray;
    }

    public static byte[] getImageFromResource(String relativeFilePath) {
        byte[] byteArray = null;
        try {
            File file = getFileFromResources(relativeFilePath);
            byteArray = org.apache.commons.io.FileUtils.readFileToByteArray(file);
        } catch (Exception e) {
            LOGGER.error(e.getStackTrace());
        }
        return byteArray;
    }
}
