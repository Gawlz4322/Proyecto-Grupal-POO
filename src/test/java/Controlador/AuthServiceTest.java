package Controlador;

import Controller.AuthService;
import Controller.UserStore;
import Model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class AuthServiceTest {

    private AuthService authService;
    private UserStore userStore;
    private String testDbPath;

    @Before
    public void setUp() {
        testDbPath = "src/test/resources/test_users.json";
        userStore = new UserStore(testDbPath);
        authService = new AuthService(userStore);
    }

    @After
    public void tearDown() {
        try {
            Files.deleteIfExists(Paths.get(testDbPath));
        } catch (Exception e) {
            // Ignorar errores de limpieza
        }
    }

    @Test
    public void testRegistrarUsuarioNuevo() {
        String username = "nuevousuario";
        char[] password = "password123".toCharArray();

        User user = authService.register(username, password);

        assertNotNull("El usuario registrado no debe ser null", user);
        assertEquals("El username debe coincidir", username, user.getUsername());
        assertNotNull("El usuario debe tener un ID", user.getId());
        assertNotNull("El usuario debe tener un hash de contraseña", user.getPasswordHash());
        assertNotNull("El usuario debe tener un salt", user.getSalt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSePuedeRegistrarUsuarioDuplicado() {
        String username = "usuarioduplicado";
        char[] password1 = "password123".toCharArray();
        char[] password2 = "password456".toCharArray();

        authService.register(username, password1);
        // Esto debe lanzar IllegalArgumentException
        authService.register(username, password2);
    }

    @Test
    public void testValidacionUsernameMinimo3Caracteres() {
        char[] password = "password123".toCharArray();

        try {
            authService.register("ab", password);
            fail("Debería lanzar excepción para username con menos de 3 caracteres");
        } catch (IllegalArgumentException e) {
            assertTrue("El mensaje debe mencionar los 3 caracteres",
                    e.getMessage().contains("3 caracteres"));
        }
    }

    @Test
    public void testValidacionUsernameSinEspacios() {
        char[] password = "password123".toCharArray();

        try {
            authService.register("usuario con espacios", password);
            fail("Debería lanzar excepción para username con espacios");
        } catch (IllegalArgumentException e) {
            assertTrue("El mensaje debe mencionar los espacios",
                    e.getMessage().contains("espacios"));
        }
    }

    @Test
    public void testValidacionPasswordMinimo8Caracteres() {
        String username = "validuser";

        try {
            authService.register(username, "corta1".toCharArray());
            fail("Debería lanzar excepción para password con menos de 8 caracteres");
        } catch (IllegalArgumentException e) {
            assertTrue("El mensaje debe mencionar los 8 caracteres",
                    e.getMessage().contains("8 caracteres"));
        }
    }

    @Test
    public void testLoginConCredencialesCorrectas() {
        String username = "usuario123";
        char[] password = "password123".toCharArray();

        // Registrar usuario
        authService.register(username, password.clone());

        // Intentar login con las mismas credenciales
        User loggedUser = authService.login(username, password.clone());

        assertNotNull("El usuario logueado no debe ser null", loggedUser);
        assertEquals("El username debe coincidir", username, loggedUser.getUsername());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginConCredencialesIncorrectas() {
        String username = "usuario456";
        char[] password = "password123".toCharArray();
        char[] wrongPassword = "wrongpassword".toCharArray();

        // Registrar usuario
        authService.register(username, password);

        // Intentar login con contraseña incorrecta (debe lanzar excepción)
        authService.login(username, wrongPassword);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoginConUsuarioQueNoExiste() {
        char[] password = "password123".toCharArray();

        // Intentar login con usuario que no existe (debe lanzar excepción)
        authService.login("usuarioinexistente", password);
    }
}