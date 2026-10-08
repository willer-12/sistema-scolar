package pe.edu.jfsc.asistencia.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "justificaciones")
public class Justification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(
            nullable = false,
            length = 500
    )
    private String motivo;

    @Column(length = 255)
    private String documento;

    @OneToOne(optional = false)
    private Attendance asistencia;

    @Column(nullable = false)
    private String registradoPor;

    // Constructor vacío requerido por JPA
    public Justification() {
    }

    // Constructor con parámetros
    public Justification(
            LocalDateTime fechaRegistro,
            String motivo,
            String documento,
            Attendance asistencia,
            String registradoPor) {

        this.fechaRegistro = fechaRegistro;
        this.motivo = motivo;
        this.documento = documento;
        this.asistencia = asistencia;
        this.registradoPor = registradoPor;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDocumento() {
        return documento;
    }

    public Attendance getAsistencia() {
        return asistencia;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }
}