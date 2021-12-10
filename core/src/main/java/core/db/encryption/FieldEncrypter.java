package core.db.encryption;

import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldEncrypter {

    public FieldEncrypter() {
    }

    public void encrypt(Object[] state, String[] propertyNames, Object entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(EncryptionUtils::isFieldEncrypted)
                .forEach(field -> encryptField(field, state, propertyNames));
        Arrays.stream(fields)
                .filter(EncryptionUtils::isFieldPasswordEncoded)
                .forEach(field -> encodePasswordField(field, state, propertyNames));
    }

    private void encryptField(Field field, Object[] state, String[] propertyNames) {
        int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[propertyIndex];
        if (currentValue != null) {
            if (!(currentValue instanceof String)) {
                throw new IllegalStateException("Encrypted annotation was used on a non-String field");
            }
            state[propertyIndex] = Encrypter.encrypt(currentValue.toString());
        }
    }

    private void encodePasswordField(Field field, Object[] state, String[] propertyNames) {
        int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
        Object currentValue = state[propertyIndex];
        if (currentValue != null) {
            if (!(currentValue instanceof String)) {
                throw new IllegalStateException("PasswordEncoded annotation was used on a non-String field");
            }
            state[propertyIndex] = PBKDF2Encoder.encode(currentValue.toString());
        }
    }
}
