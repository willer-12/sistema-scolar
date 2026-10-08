package pe.edu.jfsc.asistencia.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "asistencias",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "fecha",
                        "estudiante_id"
                }
        )
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus estado;

    @Column(length = 300)
    private String observacion;

    @ManyToOne(optional = false)
    private Student estudiante;

    @ManyToOne(optional = false)
    private Section section;

    @Column(nullable = false)
    private String registradoPor;

    // Constructor vacío requerido por JPA
    public Attendance() {
    }

    // Constructor con parámetros
    public Attendance(
            LocalDate fecha,
            LocalTime hora,
            AttendanceStatus estado,
            String observacion,
            Student estudiante,
            Section section,
            String registradoPor) {

        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.observacion = observacion;
        this.estudiante = estudiante;
        this.section = section;
        this.registradoPor = registradoPor;
    }

    // Getters

    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public AttendanceStatus getEstado() {
        return estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public Student getEstudiante() {
        return estudiante;
    }

    public Section getSection() {
        return section;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    // Setters

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setEstado(AttendanceStatus estado) {
        this.estado = estado;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setEstudiante(Student estudiante) {
        this.estudiante = estudiante;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }
}