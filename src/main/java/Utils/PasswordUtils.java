package Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Utilidades criptográficas para el manejo seguro de contraseñas.
 * Implementa hashing PBKDF2 y manejo seguro de memoria (borrado de char[]).
 */
public class PasswordUtils {
    private static final int ITERATIONS = 65536; // Iteraciones PBKDF2 para seguridad
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Genera un salt aleatorio criptográficamente seguro.
     *
     * @param length Longitud del salt en bytes.
     * @return Salt codificado en Base64.
     */
    public static String genSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Genera un hash seguro de la contraseña utilizando PBKDF2.
     *
     * @param password Contraseña en texto plano.
     * @param salt     Salt para el hashing.
     * @return Hash de la contraseña en Base64.
     */
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

    /**
     * Verifica si una contraseña coincide con el hash almacenado.
     *
     * @param password     Contraseña candidata.
     * @param salt         Salt original.
     * @param expectedHash Hash esperado.
     * @return true si coincide, false en caso contrario.
     */
    public static boolean verify(char[] password, String salt, String expectedHash) {
        String actualHash = hash(password, salt);
        return actualHash.equals(expectedHash);
    }

    /**
     * Sobrescribe un array de caracteres con ceros para borrarlo de la memoria.
     *
     * @param password Array de caracteres a limpiar.
     */
    public static void zero(char[] password) {
        if (password != null) {
            java.util.Arrays.fill(password, '\0');
        }
    }
}