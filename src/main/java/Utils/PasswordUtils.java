package Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordUtils {
    private static final int ITERATIONS = 65536; // Iteraciones PBKDF2 para seguridad
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    // Genera un salt aleatorio criptográficamente seguro
    public static String genSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hashea contraseña usando PBKDF2 con 65536 iteraciones
    public static String hash(char[] password, String salt) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            PBEKeySpec spec = new PBEKeySpec(password, saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generating password hash", e);
        }
    }

    // Verifica si una contraseña coincide con el hash almacenado
    public static boolean verify(char[] password, String salt, String expectedHash) {
        String actualHash = hash(password, salt);
        return actualHash.equals(expectedHash);
    }

    // Limpia el array de contraseña de la memoria
    public static void zero(char[] password) {
        if (password != null) {
            java.util.Arrays.fill(password, '\0');
        }
    }
}