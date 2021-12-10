package core.db.encryption;

import core.config.CoreConfiguration;
import core.config.EncryptionConfig;

public final class Encrypter {

    private static final EncryptionConfig ENCODE_PROPERTIES = CoreConfiguration.getEncodeConfig();

    private Encrypter() {
    }

    public static synchronized String encrypt(String value) {
        try {
            return AESUtils.encrypt(value, ENCODE_PROPERTIES.dataSecret());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
