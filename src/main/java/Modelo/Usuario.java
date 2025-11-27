
package Modelo;

import java.time.Instant;  // ← IMPORTANTE: Debe estar este import
import java.util.UUID;

public class Usuario {
    private String id;
    private String username;
    private String passwordHash;
    private String salt;
    private Instant createdAt;

    public Usuario() {} // para Gson

    public Usuario(String username, String passwordHash, String salt) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.createdAt = Instant.now();
    }
    
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getSalt() { return salt; }
    public Instant getCreatedAt() { return createdAt; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setSalt(String salt) { this.salt = salt; }
}