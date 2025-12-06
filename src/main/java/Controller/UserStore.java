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
 * Clase encargada de la persistencia de usuarios en formato JSON.
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
     * Constructor del almacén de usuarios.
     *
     * @param filePath Ruta del archivo JSON de usuarios.
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

    // Normaliza username a minúsculas para búsqueda case-insensitive
    private static String keyOf(String username) {
        if (username == null)
            return null;
        String n = Normalizer.normalize(username, Normalizer.Form.NFKC);
        return n.toLowerCase(Locale.ROOT).trim();
    }

    // Carga todos los usuarios desde el archivo JSON
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

    // Persiste usuarios usando escritura atómica (tmp + move)
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
     * Busca un usuario por nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Optional conteniendo el usuario si existe.
     */
    public synchronized Optional<User> findByUsername(String username) {
        return Optional.ofNullable(byUsername.get(keyOf(username)));
    }

    /**
     * Verifica si un nombre de usuario ya está registrado.
     *
     * @param username Nombre de usuario.
     * @return true si existe, false si no.
     */
    public synchronized boolean exists(String username) {
        return byUsername.containsKey(keyOf(username));
    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param user Usuario a guardar.
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
     * Actualiza los datos de un usuario existente.
     *
     * @param user Usuario con datos actualizados.
     */
    public synchronized void update(User user) {
        byUsername.put(keyOf(user.getUsername()), user);
        persist();
    }

    /**
     * Lista todos los usuarios registrados.
     *
     * @return Lista de todos los usuarios.
     */
    public synchronized List<User> listAll() {
        return new ArrayList<>(byUsername.values());
    }
}