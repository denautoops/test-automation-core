package core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

/**
 * Utils for work with JSON
 * <p>
 *
 * @author Denis.Martynov
 * Created on 29.03.21
 */
public final class JsonUtils {

    private JsonUtils() {
    }

    private static <T> T serializeJson(Class<T> tClass, File json) {
        T jsonClassObject;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.registerModule(new JavaTimeModule());
            jsonClassObject = mapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new AssertionError(String.format("We were not able to deserialize received json to the '%s' object "
                    + "because of the following core.exception: \r\n%s", tClass.getName(), e.getLocalizedMessage()));
        }
        return jsonClassObject;
    }

    public static <T> T serializeJsonFromResource(Class<T> tClass, String jsonRelativePath) {
        File jsonPath = FileUtils.getFileFromResources(jsonRelativePath);
        return serializeJson(tClass, jsonPath);
    }

}
