package core.db.encryption;

import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.reflect.Field;

public abstract class EncryptionUtils {
    public static boolean isFieldEncrypted(Field field) {
        return AnnotationUtils.findAnnotation(field, Encrypted.class).isPresent();
    }

    public static boolean isFieldPasswordEncoded(Field field) {
        return AnnotationUtils.findAnnotation(field, PasswordEncoded.class).isPresent();
    }

    public static int getPropertyIndex(String name, String[] properties) {
        for (int i = 0; i < properties.length; i++) {
            if (name.equals(properties[i])) {
                return i;
            }
        }
        throw new IllegalArgumentException("No property was found for name " + name);
    }
}
