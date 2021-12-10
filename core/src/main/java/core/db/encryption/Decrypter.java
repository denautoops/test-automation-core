package core.db.encryption;

import core.config.CoreConfiguration;
import core.config.EncryptionConfig;

public final class Decrypter {

    private static final EncryptionConfig ENCODE_PROPERTIES = CoreConfiguration.getEncodeConfig();

    private Decrypter() {
    }

    public static synchronized String decrypt(String value) {
        try {
            return AESUtils.decrypt(value, ENCODE_PROPERTIES.dataSecret());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
