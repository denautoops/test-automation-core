package core.logger.allure;

import core.utils.FileUtils;
import io.qameta.allure.Attachment;

/**
 * Utils for work with Allure
 * <p>
 *
 * @author Denis.Martynov
 * Created on 29.03.21
 */
public final class AllureUtility {

    private AllureUtility() {
    }

    private static final String ALLURE_SCREENSHOT_TYPE = "image/png";
    private static final String ALLURE_SCREENSHOT_PAGE_VALUE = "Page screenshot";
    private static final String ALLURE_ATTACHMENT_PRECONDITION_VALUE = "Precondition data";

    @Attachment(value = ALLURE_ATTACHMENT_PRECONDITION_VALUE)
    public static String attachInfo(String value) {
        return value;
    }

    @Attachment(value = ALLURE_SCREENSHOT_PAGE_VALUE, type = ALLURE_SCREENSHOT_TYPE)
    public static byte[] attachScreenshot(String filePath) {
        return FileUtils.getImageFromFile(filePath);
    }

    @Attachment(value = ALLURE_SCREENSHOT_PAGE_VALUE, type = ALLURE_SCREENSHOT_TYPE)
    public static byte[] attachScreenshot(byte[] file) {
        return file;
    }
}
