package pe.edu.jfsc.asistencia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiantes")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String codigo;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(length = 15)
    private String dni;

    @ManyToOne(optional = false)
    private Section section;

    @Column(nullable = false)
    private boolean estado = true;

    // Constructor vacío requerido por JPA
    public Student() {
    }

    // Constructor con parámetros
    public Student(
            String codigo,
            String nombres,
            String apellidos,
            String dni,
            Section section) {

        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.section = section;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public Section getSection() {
        return section;
    }

    public boolean isEstado() {
        return estado;
    }

    // Setters

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    // Nombre completo para mostrar
    public String getFullName() {
        return apellidos + ", " + nombres;
    }
}