package core.db.encryption;

import core.config.CoreConfiguration;
import core.config.EncryptionConfig;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public final class PBKDF2Encoder {

    private static final EncryptionConfig ENCODE_PROPERTIES = CoreConfiguration.getEncodeConfig();

    /**
     * Encode of password
     *
     * @param cs password
     * @return encoded password
     */
    public static synchronized String encode(CharSequence cs) {
        try {
            final byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(cs.toString().toCharArray(),
                            ENCODE_PROPERTIES.passwordSecret().getBytes(),
                            ENCODE_PROPERTIES.passwordIteration(),
                            ENCODE_PROPERTIES.passwordKeyLength()))
                    .getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Check of password with encoded password
     *
     * @param cs       password
     * @param password encoded password
     * @return encoded password
     */
    public static boolean matches(CharSequence cs, String password) {
        return encode(cs).equals(password);
    }
}
