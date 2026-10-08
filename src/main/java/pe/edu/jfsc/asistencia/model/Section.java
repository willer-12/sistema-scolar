package pe.edu.jfsc.asistencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "secciones")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String grado;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tutorUsername;

    // Constructor vacío requerido por JPA
    public Section() {
    }

    // Constructor con parámetros
    public Section(
            String grado,
            String nombre,
            String tutorUsername) {

        this.grado = grado;
        this.nombre = nombre;
        this.tutorUsername = tutorUsername;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getGrado() {
        return grado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTutorUsername() {
        return tutorUsername;
    }

    // Setters

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTutorUsername(String tutorUsername) {
        this.tutorUsername = tutorUsername;
    }

    // Nombre para mostrar
    public String getDisplayName() {
        return grado + " - " + nombre;
    }
}