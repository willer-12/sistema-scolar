package pe.edu.jfsc.asistencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "usuarios",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "username"
        )
)
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            length = 50
    )
    private String username;

    @Column(
            nullable = false,
            length = 120
    )
    private String passwordHash;

    @Column(
            nullable = false,
            length = 120
    )
    private String nombres;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private Role role;

    @Column(nullable = false)
    private boolean enabled = true;

    // Constructor vacío requerido por JPA
    public AppUser() {
    }

    // Constructor con parámetros
    public AppUser(
            String username,
            String passwordHash,
            String nombres,
            Role role) {

        this.username = username;
        this.passwordHash = passwordHash;
        this.nombres = nombres;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNombres() {
        return nombres;
    }

    public Role getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
