package core.db.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public final class AESUtils {

    private AESUtils() {
    }

    public static synchronized String encrypt(String strToEncrypt, String secret) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(secret));
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
    }

    public static synchronized String decrypt(String strToDecrypt, String secret) throws Exception{

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(secret));
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

    }

    private static synchronized SecretKeySpec getSecretKeySpec(String secret) throws NoSuchAlgorithmException {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }
}
