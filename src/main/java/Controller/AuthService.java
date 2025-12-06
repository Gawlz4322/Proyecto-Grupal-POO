package Controller;

import Model.User;
import Utils.PasswordUtils;

public class AuthService {
    private final UserStore store;

    public AuthService(UserStore store) {
        this.store = store;
    }
    public User register(String username, char[] password) {
        validateUsername(username);
        validatePassword(password);
        if (store.exists(username))
            throw new IllegalArgumentException("El usuario ya existe");

        String salt = PasswordUtils.genSalt(16);
        String hash = PasswordUtils.hash(password, salt);
        try {
            User u = new User(username.trim(), hash, salt);
            store.saveNew(u);
            return u;
        } finally {
            // Limpia la contraseña de la memoria
            PasswordUtils.zero(password);
        }
    }
    public User login(String username, char[] password) {
        User u = store.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos"));

        boolean ok = PasswordUtils.verify(password, u.getSalt(), u.getPasswordHash());
        PasswordUtils.zero(password); // Limpia contraseña de memoria
        if (!ok)
            throw new IllegalArgumentException("Usuario o contraseña inválidos");
        return u;
    }

    // Cambia la contraseña de un usuario existente
    public void changePassword(String username, char[] oldPass, char[] newPass) {
        User u = store.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        boolean ok = PasswordUtils.verify(oldPass, u.getSalt(), u.getPasswordHash());
        PasswordUtils.zero(oldPass);
        if (!ok)
            throw new IllegalArgumentException("Contraseña actual incorrecta");

        validatePassword(newPass);
        String newSalt = PasswordUtils.genSalt(16);
        String newHash = PasswordUtils.hash(newPass, newSalt);
        PasswordUtils.zero(newPass);

        u.setSalt(newSalt);
        u.setPasswordHash(newHash);
        store.update(u);
    }

    // Valida formato de nombre de usuario
    private void validateUsername(String username) {
        if (username == null || username.trim().length() < 3)
            throw new IllegalArgumentException("El nombre de usuario debe tener al menos 3 caracteres");
        if (username.contains(" "))
            throw new IllegalArgumentException("El nombre de usuario no puede contener espacios");
    }

    // Valida longitud mínima de contraseña
    private void validatePassword(char[] password) {
        if (password == null || password.length < 8)
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
    }
}