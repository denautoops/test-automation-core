package core.db.encryption;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldDecrypter {

    public FieldDecrypter() {
    }

    public void decrypt(Object[] state, String[] propertyNames, Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(EncryptionUtils::isFieldEncrypted)
                .forEach(field -> decryptField(field, state, propertyNames));
    }

    private void decryptField(Field field, Object[] state, String[] propertyNames) {
        int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[propertyIndex];
        if (currentValue != null) {
            if (!(currentValue instanceof String)) {
                throw new IllegalStateException("Encrypted annotation was used on a non-String field");
            }
            state[propertyIndex] = Decrypter.decrypt(currentValue.toString());
        }
    }
}
