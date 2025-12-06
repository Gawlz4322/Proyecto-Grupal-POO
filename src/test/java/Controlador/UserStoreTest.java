package Controlador;

import Controller.UserStore;
import Model.User;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class UserStoreTest {

    private static final String TEST_DATA_DIR = "data/test";
    private static final String TEST_USER_FILE = TEST_DATA_DIR + "/users_test.json";

    @After
    public void cleanup() throws IOException {
        // Clean up test files after each test
        Path testFile = Paths.get(TEST_USER_FILE);
        if (Files.exists(testFile)) {
            Files.delete(testFile);
        }
        Path testDir = Paths.get(TEST_DATA_DIR);
        if (Files.exists(testDir)) {
            Files.delete(testDir);
        }
    }

    @Test
    public void testCreateUserStoreWithNonExistentFile() {
        // Given: A file path that doesn't exist
        String nonExistentPath = TEST_USER_FILE;
        Path path = Paths.get(nonExistentPath);

        // When: Creating a UserStore with this path
        UserStore store = new UserStore(nonExistentPath);

        // Then: UserStore should be created successfully (file doesn't need to
        // pre-exist)
        assertNotNull("UserStore should be created", store);
        assertTrue("listAll should return empty list", store.listAll().isEmpty());
    }
}
