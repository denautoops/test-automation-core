package core.assertions;

import core.logger.Logger;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class SoftAssertions {

    private static final Logger LOGGER = Logger.getInstance(SoftAssertions.class);

    private static final String ERROR_PATTERN = System.lineSeparator() + "%s" + System.lineSeparator()
        + "Expected: '%s'." + System.lineSeparator() + "Actual: '%s'" + System.lineSeparator();

    private static ThreadLocal<ArrayList<String>> store = new ThreadLocal<>();

    private SoftAssertions() {
    }

    public static ThreadLocal<ArrayList<String>> getStore() {
        return store;
    }

    public static void initStore() {
        store.set(new ArrayList<>());
    }

    public static void clearStorage() {
        store.get().clear();
    }

    public static List<String> getErrors() {
        return store.get();
    }

    private static void addError(String message) {
        store.get().add(message);
        LOGGER.error(message);
    }

    private static void addError(String expected, String actual, String message) {
        addError(String.format(ERROR_PATTERN, message, expected, actual));
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            addError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            addError(message);
        }
    }

    public static void assertNull(Object nullObject, String message) {
        if (Objects.nonNull(nullObject)) {
            addError(message + " Expected object: " + nullObject.toString());
        }
    }

    public static void hardAssertNotNull(Object nullObject, String message) {
        Assertions.assertTrue(Objects.nonNull(nullObject), message + " Expected object is null !");
    }

    public static void assertEquals(String expected, String actual, String message) {
        LOGGER.allureInfo("Verify that the string is " + expected);
        if ((Objects.isNull(expected) && Objects.nonNull(actual)) || !expected.equals(actual)) {
            addError(expected, actual, message);
        }
    }

    public static void assertEquals(Object expected, Object actual, String message) {
        LOGGER.allureInfo("Verify that the string is " + expected);
        if ((Objects.isNull(expected) && Objects.nonNull(actual)) || !expected.equals(actual)) {
            addError(expected.toString(), actual.toString(), message);
        }
    }

    public static void assertEqualsInt(Integer expected, Integer actual, String message) {
        if ((Objects.isNull(expected) && Objects.nonNull(actual)) || !expected.equals(actual)) {
            String expectedString = Objects.isNull(expected) ? "null" : expected.toString();
            String actualString = Objects.isNull(actual) ? "null" : actual.toString();
            addError(expectedString, actualString, message);
        }
    }

    public static void shouldContain(String expected, List<String> actualList, String message) {
        if (!actualList.contains(expected)) {
            addError(expected, String.join(", ", actualList), message);
        }
    }

    public static void shouldNotContain(String expected, List<String> actualList, String message) {
        if (actualList.contains(expected)) {
            addError(expected, String.join(", ", actualList), message);
        }
    }

    public static void assertFileContentEquals(String pathToExpected, String pathToActual, String message)
        throws IOException {
        String expectedContent = String.join(File.separator, Files.readAllLines(Paths.get(pathToExpected)));
        String actualContent = String.join(File.separator, Files.readAllLines(Paths.get(pathToActual)));
        assertEquals(expectedContent, actualContent, message);
    }

    public static void hardAssertTrue(boolean condition, String message) {
        Assertions.assertTrue(condition, message);
    }

    public static void hardAssertEquals(int expected, int actual, String message) {
        Assertions.assertEquals(expected, actual, message);
    }

    public static void hardAssertErrorStorage() {
        Assertions.assertTrue(store.get().isEmpty(),
            String.format("Test execution found %s errors: %s %s",
                store.get().size(), System.lineSeparator(), String.join(System.lineSeparator(), store.get())));
    }
}
