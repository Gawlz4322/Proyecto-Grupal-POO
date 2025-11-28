package Controller;

import Model.FinanceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona el almacenamiento y recuperación de datos financieros en un archivo
 * JSON.
 */
public class FinanceStore {
    private final Path jsonPath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, FinanceData> dataCache = new HashMap<>();
    private static final Type MAP_TYPE = new TypeToken<Map<String, FinanceData>>() {
    }.getType();

    /**
     * Inicializa el almacenamiento de finanzas.
     *
     * @param filePath Ruta al archivo JSON de finanzas.
     */
    public FinanceStore(String filePath) {
        this.jsonPath = Paths.get(filePath);
        ensureDataDirectory();
        loadAll();
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

    private synchronized void loadAll() {
        dataCache.clear();
        if (Files.notExists(jsonPath))
            return;
        try (Reader r = Files.newBufferedReader(jsonPath)) {
            Map<String, FinanceData> loaded = gson.fromJson(r, MAP_TYPE);
            if (loaded != null) {
                dataCache.putAll(loaded);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo leer " + jsonPath, e);
        }
    }

    private synchronized void persist() {
        try {
            Path tmp = jsonPath.resolveSibling(jsonPath.getFileName() + ".tmp");
            try (Writer w = Files.newBufferedWriter(tmp,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                gson.toJson(dataCache, MAP_TYPE, w);
            }
            Files.move(tmp, jsonPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo escribir " + jsonPath, e);
        }
    }

    /**
     * Carga los datos financieros de un usuario.
     * Si no existen datos, crea una nueva instancia.
     *
     * @param userId El ID del usuario.
     * @return Los datos financieros del usuario.
     */
    public synchronized FinanceData load(String userId) {
        return dataCache.computeIfAbsent(userId, FinanceData::new);
    }

    /**
     * Guarda los datos financieros de un usuario.
     *
     * @param data Los datos financieros a guardar.
     */
    public synchronized void save(FinanceData data) {
        dataCache.put(data.getUserId(), data);
        persist();
    }
}
