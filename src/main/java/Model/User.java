package Model;

import java.time.Instant; // ← IMPORTANTE: Debe estar este import
import java.util.UUID;

/**
 * Representa un usuario en el sistema.
 * Contiene información de identificación, credenciales y fecha de creación.
 */
public class User {
    private String id;
    private String username;
    private String passwordHash;
    private String salt;
    private Instant createdAt;

    /**
     * Constructor vacío requerido por Gson para la deserialización.
     */
    public User() {
    } // para Gson

    /**
     * Crea un nuevo usuario con el nombre de usuario, hash de contraseña y salt
     * proporcionados.
     * Genera un ID único y establece la fecha de creación actual.
     *
     * @param username     El nombre de usuario.
     * @param passwordHash El hash de la contraseña.
     * @param salt         El salt utilizado para el hash.
     */
    public User(String username, String passwordHash, String salt) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}