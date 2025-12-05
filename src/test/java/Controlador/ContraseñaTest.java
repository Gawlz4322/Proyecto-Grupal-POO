package Controlador;

import Utils.PasswordUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContraseñaTest {

    @Test
    public void testGenerarSaltAleatorio() {
        String salt1 = PasswordUtils.genSalt(16);
        String salt2 = PasswordUtils.genSalt(16);

        assertNotNull("El salt no debe ser null", salt1);
        assertNotNull("El segundo salt no debe ser null", salt2);
        assertNotEquals("Los salts deben ser diferentes (aleatorios)", salt1, salt2);
        assertTrue("El salt debe tener contenido", salt1.length() > 0);
    }

    @Test
    public void testHashearContrasena() {
        char[] password = "micontraseña123".toCharArray();
        String salt = PasswordUtils.genSalt(16);

        String hash = PasswordUtils.hash(password, salt);

        assertNotNull("El hash no debe ser null", hash);
    }

    @Test
    public void testHashNoEsVacio() {
        char[] password = "password123".toCharArray();
        String salt = PasswordUtils.genSalt(16);

        String hash = PasswordUtils.hash(password, salt);

        assertNotNull("El hash no debe ser null", hash);
        assertTrue("El hash no debe estar vacío", hash.length() > 0);
        assertNotEquals("El hash no debe ser igual a la contraseña original",
                new String(password), hash);
    }

    @Test
    public void testVerificarContrasenaCorrecta() {
        char[] password = "contraseñasegura".toCharArray();
        String salt = PasswordUtils.genSalt(16);
        String hash = PasswordUtils.hash(password.clone(), salt);

        boolean resultado = PasswordUtils.verify(password, salt, hash);

        assertTrue("La verificación debe ser exitosa con la contraseña correcta", resultado);
    }

    @Test
    public void testVerificarContrasenaIncorrecta() {
        char[] passwordCorrecta = "contraseñacorrecta".toCharArray();
        char[] passwordIncorrecta = "contraseñaincorrecta".toCharArray();
        String salt = PasswordUtils.genSalt(16);
        String hash = PasswordUtils.hash(passwordCorrecta, salt);

        boolean resultado = PasswordUtils.verify(passwordIncorrecta, salt, hash);

        assertFalse("La verificación debe fallar con contraseña incorrecta", resultado);
    }

    @Test
    public void testMismoPasswordConMismoSaltDaMismoHash() {
        char[] password = "password123".toCharArray();
        String salt = PasswordUtils.genSalt(16);

        String hash1 = PasswordUtils.hash(password.clone(), salt);
        String hash2 = PasswordUtils.hash(password.clone(), salt);

        assertEquals("El mismo password con el mismo salt debe dar el mismo hash",
                hash1, hash2);
    }

    @Test
    public void testMismoPasswordConDiferenteSaltDaDiferenteHash() {
        char[] password = "password123".toCharArray();
        String salt1 = PasswordUtils.genSalt(16);
        String salt2 = PasswordUtils.genSalt(16);

        String hash1 = PasswordUtils.hash(password.clone(), salt1);
        String hash2 = PasswordUtils.hash(password.clone(), salt2);

        assertNotEquals("El mismo password con diferente salt debe dar diferente hash",
                hash1, hash2);
    }

    @Test
    public void testLimpiarArrayDeContrasena() {
        char[] password = "password123".toCharArray();
        char[] passwordOriginal = password.clone();

        PasswordUtils.zero(password);

        // Verificar que el array fue limpiado
        for (char c : password) {
            assertEquals("Todos los caracteres deben ser '\\0' después de zero", '\0', c);
        }

        // Verificar que el array original no fue modificado (para confirmar que zero
        // funciona)
        assertNotEquals("El array debe haber sido modificado",
                new String(passwordOriginal), new String(password));
    }

    @Test
    public void testDespuesDeZeroArrayEstaLlenoDeCeros() {
        char[] password = "supersecret".toCharArray();
        int longitud = password.length;

        PasswordUtils.zero(password);

        assertEquals("La longitud del array no debe cambiar", longitud, password.length);

        // Verificar que todos los caracteres son '\0'
        for (int i = 0; i < password.length; i++) {
            assertEquals("El carácter en posición " + i + " debe ser '\\0'",
                    '\0', password[i]);
        }
    }
}
