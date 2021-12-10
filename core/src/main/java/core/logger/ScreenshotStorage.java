package core.logger;

import core.logger.allure.AllureUtility;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Screenshot Storage
 * <p>
 *
 * @author Denis.Martynov
 * Created on 30.03.21
 */
public class ScreenshotStorage {

    private static final Logger LOGGER = Logger.getInstance(ScreenshotStorage.class);

    private ThreadLocal<ArrayList<byte[]>> screenshotStorage;

    public ScreenshotStorage() {
        this.screenshotStorage = new ThreadLocal<>();
        this.screenshotStorage.set(new ArrayList<>());
    }

    public void addScreenshot(final byte[] screenshot) {
        screenshotStorage.get().add(screenshot);
    }

    public void releaseStorage(boolean isTestFailed) {
        ArrayList<byte[]> screenshots = screenshotStorage.get();

        if (isTestFailed) {
            if (!screenshots.isEmpty()) {
                screenshots.stream()
                        .filter(Objects::nonNull)
                        .forEach(AllureUtility::attachScreenshot);
            }
        }

        screenshotStorage.get().clear();
    }

}
