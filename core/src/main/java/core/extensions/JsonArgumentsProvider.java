package core.extensions;

import core.utils.JsonUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import java.util.stream.Stream;

/**
 * <p>
 *
 * @author Denis.Martynov
 * Created on 9.04.21
 */
public class JsonArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<JsonSource> {

    private String resource;
    private Class<?> type;

    @Override
    public void accept(JsonSource jsonSource) {
        resource = jsonSource.resource();
        type = jsonSource.type();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        if (type.isArray()) {
            Object[] arrayOfPojo = (Object[]) JsonUtils.serializeJsonFromResource(type, resource);
            return Stream.of(arrayOfPojo).map(Arguments::of);
        } else {
            Object pojo = JsonUtils.serializeJsonFromResource(type, resource);
            return Stream.of(pojo).map(Arguments::of);
        }
    }
}
