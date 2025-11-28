package Controller;

import Model.User;
import Utils.InstantAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.text.Normalizer;
import java.time.Instant;
import java.util.*;

/**
 * Gestiona el almacenamiento y recuperación de usuarios en un archivo JSON.
 */
public class UserStore {
    private final Path jsonPath;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Instant.class, new InstantAdapter())
            .create();
    private static final Type LIST_TYPE = new TypeToken<List<User>>() {
    }.getType();
    private final Map<String, User> byUsername = new HashMap<>();

    /**
     * Inicializa el almacenamiento de usuarios.
     *
     * @param filePath Ruta al archivo JSON de usuarios.
     */
    public UserStore(String filePath) {
        this.jsonPath = Paths.get(filePath);
        ensureDataDirectory();
        load();
    }

    private void ensureDataDirectory() {
        try {
            Path parent = jsonPath.getParent();
            if (parent != null && Files.notExists(parent)) {
                Files.createDirectories(parent);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear el directorio de datos", e);
        }
    }

    private static String keyOf(String username) {
        if (username == null)
            return null;
        String n = Normalizer.normalize(username, Normalizer.Form.NFKC);
        return n.toLowerCase(Locale.ROOT).trim();
    }

    private synchronized void load() {
        byUsername.clear();
        try {
            if (Files.notExists(jsonPath))
                return;
            try (Reader r = Files.newBufferedReader(jsonPath)) {
                List<User> list = gson.fromJson(r, LIST_TYPE);
                if (list != null)
                    for (User u : list)
                        byUsername.put(keyOf(u.getUsername()), u);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer " + jsonPath, e);
        }
    }

    private synchronized void persist() {
        try {
            List<User> list = new ArrayList<>(byUsername.values());
            Path tmp = jsonPath.resolveSibling(jsonPath.getFileName() + ".tmp");
            try (Writer w = Files.newBufferedWriter(tmp,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                gson.toJson(list, LIST_TYPE, w);
            }
            Files.move(tmp, jsonPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo escribir " + jsonPath, e);
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional conteniendo el usuario si existe.
     */
    public synchronized Optional<User> findByUsername(String username) {
        return Optional.ofNullable(byUsername.get(keyOf(username)));
    }

    /**
     * Verifica si existe un usuario con el nombre dado.
     *
     * @param username El nombre de usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public synchronized boolean exists(String username) {
        return byUsername.containsKey(keyOf(username));
    }

    /**
     * Guarda un nuevo usuario en el almacenamiento.
     *
     * @param user El usuario a guardar.
     * @throws IllegalArgumentException Si el usuario ya existe.
     */
    public synchronized void saveNew(User user) {
        String k = keyOf(user.getUsername());
        if (byUsername.containsKey(k))
            throw new IllegalArgumentException("El usuario ya existe");
        byUsername.put(k, user);
        persist();
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param user El usuario con la información actualizada.
     */
    public synchronized void update(User user) {
        byUsername.put(keyOf(user.getUsername()), user);
        persist();
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * @return Una lista de todos los usuarios.
     */
    public synchronized List<User> listAll() {
        return new ArrayList<>(byUsername.values());
    }
}