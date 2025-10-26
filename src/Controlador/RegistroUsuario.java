package Controlador;

import Modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.text.Normalizer;
import java.util.*;

public class UserStore {
    private final Path jsonPath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type LIST_TYPE = new TypeToken<List<User>>(){}.getType();
    private final Map<String, User> byUsername = new HashMap<>();

    public UserStore(String filePath) {
        this.jsonPath = Paths.get(filePath);
        load();
    }

    private static String keyOf(String username) {
        if (username == null) return null;
        String n = Normalizer.normalize(username, Normalizer.Form.NFKC);
        return n.toLowerCase(Locale.ROOT).trim();
    }

    private synchronized void load() {
        byUsername.clear();
        try {
            if (Files.notExists(jsonPath)) return;
            try (Reader r = Files.newBufferedReader(jsonPath)) {
                List<User> list = gson.fromJson(r, LIST_TYPE);
                if (list != null) for (User u : list) byUsername.put(keyOf(u.getUsername()), u);
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

    public synchronized Optional<User> findByUsername(String username) {
        return Optional.ofNullable(byUsername.get(keyOf(username)));
    }

    public synchronized boolean exists(String username) {
        return byUsername.containsKey(keyOf(username));
    }

    public synchronized void saveNew(User user) {
        String k = keyOf(user.getUsername());
        if (byUsername.containsKey(k)) throw new IllegalArgumentException("El usuario ya existe");
        byUsername.put(k, user);
        persist();
    }

    public synchronized void update(User user) {
        byUsername.put(keyOf(user.getUsername()), user);
        persist();
    }

    public synchronized List<User> listAll() {
        return new ArrayList<>(byUsername.values());
    }
}