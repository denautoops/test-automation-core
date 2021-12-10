package core.logger;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;

/**
 * Logger
 * <p>
 *
 * @author Denis.Martynov
 * Created on 29.03.21
 */
public final class Logger {

    private final org.apache.logging.log4j.Logger logger;

    private Logger() {
        this(Logger.class);
    }

    private Logger(Class<?> clazz) {
        logger = LogManager.getLogger(clazz);
    }

    public static Logger getInstance() {
        return new Logger();
    }

    public static Logger getInstance(Class<?> clazz) {
        return new Logger(clazz);
    }

    public void info(Object message) {
        logger.info(message);
    }

    public void debug(Object message) {
        logger.debug(message);
    }

    public void testStartInfo(String testName) {
        info(String.format("========== Test '%s' started ==========", testName));
    }

    public void testEndInfo(String testName) {
        info(String.format("=========== Test '%s' ended ===========", testName));
    }

    public void stepInfo(Object message) {
        allureInfo(">>>   STEP: " + message);
    }

    @Step("{0}")
    public void allureInfo(Object message) {
        info(message);
    }

    public void warn(Object message) {
        logger.warn(message);
    }

    @Step("Warn: {0}")
    public void allureWarn(Object message) {
        warn(message);
    }

    public void error(Object message) {
        logger.error(message);
    }

    @Step("Error: {0}")
    public void allureError(Object message) {
        error(message);
    }

    public void fatal(Object message) {
        logger.fatal(message);
    }

    @Step("Fatal: {0}")
    public void allureFatal(Object message) {
        fatal(message);
    }
}
