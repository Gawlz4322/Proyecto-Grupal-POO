package Controlador;

import Controller.UserStore;
import Model.User;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

    @Test
    public void testDataDirectoryIsCreatedIfNotExists() {
        // Given: A file path where the parent directory doesn't exist
        Path testDir = Paths.get(TEST_DATA_DIR);
        assertFalse("Test directory should not exist initially", Files.exists(testDir));

        // When: Creating a UserStore with this path
        UserStore store = new UserStore(TEST_USER_FILE);

        // Then: The parent directory should be created automatically
        assertTrue("Data directory should be created", Files.exists(testDir));
        assertTrue("Data directory should be a directory", Files.isDirectory(testDir));
    }

    @Test
    public void testSaveNewUser() {
        // Given: A UserStore and a new user
        UserStore store = new UserStore(TEST_USER_FILE);
        User newUser = new User("testuser", "hashedPassword123", "salt123");

        // When: Saving the new user
        store.saveNew(newUser);

        // Then: The user should exist in the store
        assertTrue("User should exist after saving", store.exists("testuser"));
        assertEquals("List should contain 1 user", 1, store.listAll().size());
    }

    @Test
    public void testFindUserByUsername() {
        // Given: A UserStore with a saved user
        UserStore store = new UserStore(TEST_USER_FILE);
        User user = new User("john_doe", "hash456", "salt456");
        store.saveNew(user);

        // When: Finding the user by username
        Optional<User> found = store.findByUsername("john_doe");

        // Then: The user should be found
        assertTrue("User should be found", found.isPresent());
        assertEquals("Username should match", "john_doe", found.get().getUsername());
    }

    @Test
    public void testFindNonExistentUserReturnsEmpty() {
        // Given: A UserStore without any users
        UserStore store = new UserStore(TEST_USER_FILE);

        // When: Searching for a non-existent user
        Optional<User> found = store.findByUsername("nonexistent");

        // Then: Optional.empty() should be returned
        assertFalse("Should not find non-existent user", found.isPresent());
    }

    @Test
    public void testUsernameIsCaseInsensitive() {
        // Given: A UserStore with a user saved with mixed case username
        UserStore store = new UserStore(TEST_USER_FILE);
        User user = new User("Miguel", "hash789", "salt789");
        store.saveNew(user);

        // When: Searching with different case variations
        Optional<User> foundLower = store.findByUsername("miguel");
        Optional<User> foundUpper = store.findByUsername("MIGUEL");
        Optional<User> foundMixed = store.findByUsername("MiGuEl");

        // Then: All variations should find the same user
        assertTrue("Should find user with lowercase", foundLower.isPresent());
        assertTrue("Should find user with uppercase", foundUpper.isPresent());
        assertTrue("Should find user with mixed case", foundMixed.isPresent());
        assertEquals("All should be the same user", foundLower.get().getUsername(),
                foundUpper.get().getUsername());
    }

    @Test
    public void testExistsReturnsTrueWhenUserExists() {
        // Given: A UserStore with a saved user
        UserStore store = new UserStore(TEST_USER_FILE);
        User user = new User("existinguser", "hashABC", "saltABC");
        store.saveNew(user);

        // When: Checking if the user exists
        boolean exists = store.exists("existinguser");

        // Then: exists() should return true
        assertTrue("exists should return true for existing user", exists);
    }

    @Test
    public void testExistsReturnsFalseWhenUserDoesNotExist() {
        // Given: A UserStore without the searched user
        UserStore store = new UserStore(TEST_USER_FILE);

        // When: Checking if a non-existent user exists
        boolean exists = store.exists("nonexistentuser");

        // Then: exists() should return false
        assertFalse("exists should return false for non-existent user", exists);
    }

    @Test
    public void testUpdateExistingUser() {
        // Given: A UserStore with an existing user
        UserStore store = new UserStore(TEST_USER_FILE);
        User user = new User("updateuser", "oldHash", "oldSalt");
        store.saveNew(user);

        // When: Updating the user with new password hash and salt
        user.setPasswordHash("newHash");
        user.setSalt("newSalt");
        store.update(user);

        // Then: The updated information should be persisted
        Optional<User> updated = store.findByUsername("updateuser");
        assertTrue("User should still exist", updated.isPresent());
        assertEquals("Password hash should be updated", "newHash", updated.get().getPasswordHash());
        assertEquals("Salt should be updated", "newSalt", updated.get().getSalt());
    }

    @Test
    public void testListAllUsers() {
        // Given: A UserStore with multiple users
        UserStore store = new UserStore(TEST_USER_FILE);
        store.saveNew(new User("user1", "hash1", "salt1"));
        store.saveNew(new User("user2", "hash2", "salt2"));
        store.saveNew(new User("user3", "hash3", "salt3"));

        // When: Listing all users
        List<User> allUsers = store.listAll();

        // Then: All users should be returned
        assertEquals("Should have 3 users", 3, allUsers.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotSaveDuplicateUser() {
        // Given: A UserStore with an existing user
        UserStore store = new UserStore(TEST_USER_FILE);
        store.saveNew(new User("duplicate", "hash1", "salt1"));

        // When: Attempting to save another user with the same username
        // Then: Should throw IllegalArgumentException
        store.saveNew(new User("duplicate", "hash2", "salt2"));
    }

    @Test
    public void testJsonFileIsCreatedCorrectly() {
        // Given: A UserStore and a new user
        UserStore store = new UserStore(TEST_USER_FILE);
        User user = new User("jsonuser", "jsonHash", "jsonSalt");

        // When: Saving the user
        store.saveNew(user);

        // Then: The JSON file should be created
        Path jsonFile = Paths.get(TEST_USER_FILE);
        assertTrue("JSON file should exist", Files.exists(jsonFile));
        assertTrue("JSON file should be a regular file", Files.isRegularFile(jsonFile));
    }

    @Test
    public void testLoadUsersFromJsonFile() {
        // Given: A UserStore with saved users
        UserStore store1 = new UserStore(TEST_USER_FILE);
        store1.saveNew(new User("user_a", "hashA", "saltA"));
        store1.saveNew(new User("user_b", "hashB", "saltB"));

        // When: Creating a new UserStore instance with the same file path
        UserStore store2 = new UserStore(TEST_USER_FILE);

        // Then: The new instance should load all users from the JSON file
        assertEquals("Should load 2 users from file", 2, store2.listAll().size());
        assertTrue("Should find user_a", store2.exists("user_a"));
        assertTrue("Should find user_b", store2.exists("user_b"));
    }
}
