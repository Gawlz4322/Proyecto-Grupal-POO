package Model;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa a un usuario del sistema.
 * Contiene credenciales y metadatos básicos.
 */
public class User {
    private String id;
    private String username;
    private String passwordHash;
    private String salt;
    private Instant createdAt;

    /**
     * Constructor por defecto necesario para frameworks de serialización.
     */
    public User() {
    }

    /**
     * Crea un nuevo usuario.
     *
     * @param username     Nombre de usuario.
     * @param passwordHash Hash de la contraseña.
     * @param salt         Salt utilizado para hashear.
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