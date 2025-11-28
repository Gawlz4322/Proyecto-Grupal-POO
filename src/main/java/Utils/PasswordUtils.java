package Utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Utilidades para el manejo seguro de contraseñas.
 * Proporciona funciones para generar salt, hashear contraseñas y verificarlas.
 */
public class PasswordUtils {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * Genera un salt aleatorio de la longitud especificada.
     *
     * @param length La longitud del salt en bytes.
     * @return El salt codificado en Base64.
     */
    public static String genSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Genera un hash de la contraseña utilizando PBKDF2.
     *
     * @param password La contraseña en texto plano.
     * @param salt     El salt a utilizar.
     * @return El hash de la contraseña codificado en Base64.
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
     * Verifica si una contraseña coincide con un hash y salt dados.
     *
     * @param password     La contraseña a verificar.
     * @param salt         El salt utilizado para el hash original.
     * @param expectedHash El hash esperado.
     * @return true si la contraseña coincide, false en caso contrario.
     */
    public static boolean verify(char[] password, String salt, String expectedHash) {
        String actualHash = hash(password, salt);
        return actualHash.equals(expectedHash);
    }

    /**
     * Limpia un array de caracteres de la memoria sobrescribiéndolo con nulos.
     *
     * @param password El array de caracteres a limpiar.
     */
    public static void zero(char[] password) {
        if (password != null) {
            java.util.Arrays.fill(password, '\0');
        }
    }
}