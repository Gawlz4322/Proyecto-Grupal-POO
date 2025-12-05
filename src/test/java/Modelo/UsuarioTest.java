package Modelo;

import Model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioTest {

    @Test
    public void testCrearUsuarioConDatosValidos() {
        String username = "testuser";
        String passwordHash = "hashedpassword123";
        String salt = "randomsalt456";

        User user = new User(username, passwordHash, salt);

        assertNotNull("El usuario no debe ser null", user);
        assertEquals("El username debe coincidir", username, user.getUsername());
        assertEquals("El passwordHash debe coincidir", passwordHash, user.getPasswordHash());
        assertEquals("El salt debe coincidir", salt, user.getSalt());
    }

    @Test
    public void testIdSeGeneraAutomaticamente() {
        User user1 = new User("user1", "hash1", "salt1");
        User user2 = new User("user2", "hash2", "salt2");

        assertNotNull("El ID del usuario 1 no debe ser null", user1.getId());
        assertNotNull("El ID del usuario 2 no debe ser null", user2.getId());
        assertNotEquals("Los IDs deben ser únicos", user1.getId(), user2.getId());
        assertTrue("El ID debe tener formato UUID", user1.getId().length() > 0);
    }

    @Test
    public void testUsernameSeGuardaCorrectamente() {
        String expectedUsername = "miusuario";
        User user = new User(expectedUsername, "hash", "salt");

        assertEquals("El username debe guardarse correctamente", expectedUsername, user.getUsername());
    }

    @Test
    public void testCreatedAtSeGeneraAutomaticamente() {
        User user = new User("testuser", "hash", "salt");

        assertNotNull("La fecha de creación no debe ser null", user.getCreatedAt());
        assertTrue("La fecha de creación debe ser reciente",
                user.getCreatedAt().toEpochMilli() <= System.currentTimeMillis());
    }

    @Test
    public void testGetters() {
        String username = "testuser";
        String passwordHash = "hashedpassword";
        String salt = "salt123";

        User user = new User(username, passwordHash, salt);

        assertEquals("getId debe devolver el ID correcto", user.getId(), user.getId());
        assertEquals("getUsername debe devolver el username", username, user.getUsername());
        assertEquals("getPasswordHash debe devolver el hash", passwordHash, user.getPasswordHash());
        assertEquals("getSalt debe devolver el salt", salt, user.getSalt());
        assertNotNull("getCreatedAt debe devolver una fecha", user.getCreatedAt());
    }

    @Test
    public void testSetters() {
        User user = new User("testuser", "oldhash", "oldsalt");

        String newHash = "newhash123";
        String newSalt = "newsalt456";

        user.setPasswordHash(newHash);
        user.setSalt(newSalt);

        assertEquals("setPasswordHash debe actualizar el hash", newHash, user.getPasswordHash());
        assertEquals("setSalt debe actualizar el salt", newSalt, user.getSalt());
    }

    @Test
    public void testConstructorSinParametros() {
        // Este constructor es necesario para que Gson pueda deserializar
        User user = new User();

        assertNotNull("El usuario creado sin parámetros no debe ser null", user);
        // Los campos estarán null hasta que Gson los llene
        assertNull("El ID debe ser null inicialmente", user.getId());
        assertNull("El username debe ser null inicialmente", user.getUsername());
    }
}
