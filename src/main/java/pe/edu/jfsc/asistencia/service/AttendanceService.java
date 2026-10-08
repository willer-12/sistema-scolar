package pe.edu.jfsc.asistencia.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.jfsc.asistencia.model.Attendance;
import pe.edu.jfsc.asistencia.model.AttendanceStatus;
import pe.edu.jfsc.asistencia.model.Section;
import pe.edu.jfsc.asistencia.model.Student;
import pe.edu.jfsc.asistencia.repository.AttendanceRepository;
import pe.edu.jfsc.asistencia.repository.SectionRepository;
import pe.edu.jfsc.asistencia.repository.StudentRepository;

@Service
public class AttendanceService {

    private final AttendanceRepository attendance;
    private final StudentRepository students;
    private final SectionRepository sections;

    public AttendanceService(
            AttendanceRepository attendance,
            StudentRepository students,
            SectionRepository sections) {

        this.attendance = attendance;
        this.students = students;
        this.sections = sections;
    }

    // Obtener estudiantes de una sección
    public List<Student> studentsFor(Long sectionId) {

        return students.findBySectionIdOrderByApellidosAscNombresAsc(
                sectionId
        );
    }

    // Guardar las asistencias de una sección
    @Transactional
    public void saveBulk(
            Long sectionId,
            LocalDate fecha,
            Map<Long, String> estados,
            Map<Long, String> observaciones,
            String usuario) {

        Section section = sections.findById(sectionId)
                .orElseThrow();

        for (Student student : studentsFor(sectionId)) {

            String rawStatus = estados.get(student.getId());

            // Si no se seleccionó un estado, continuar con el siguiente estudiante
            if (rawStatus == null) {
                continue;
            }

            AttendanceStatus status =
                    AttendanceStatus.valueOf(rawStatus);

            Attendance attendanceRecord =
                    attendance
                            .findByFechaAndEstudianteId(
                                    fecha,
                                    student.getId()
                            )
                            .orElse(new Attendance());

            attendanceRecord.setFecha(fecha);
            attendanceRecord.setHora(
                    LocalTime.now().withNano(0)
            );
            attendanceRecord.setEstado(status);
            attendanceRecord.setObservacion(
                    observaciones.get(student.getId())
            );
            attendanceRecord.setEstudiante(student);
            attendanceRecord.setSection(section);
            attendanceRecord.setRegistradoPor(usuario);

            attendance.save(attendanceRecord);
        }
    }

    // Generar reporte de asistencias
    public List<Attendance> report(
            LocalDate desde,
            LocalDate hasta,
            Long sectionId,
            Long studentId) {

        // Reporte filtrado por estudiante
        if (studentId != null) {

            return attendance
                    .findByEstudianteIdAndFechaBetweenOrderByFechaDesc(
                            studentId,
                            desde,
                            hasta
                    );
        }

        // Reporte filtrado por sección
        if (sectionId != null) {

            return attendance
                    .findByEstudianteSectionIdAndFechaBetweenOrderByFechaDesc(
                            sectionId,
                            desde,
                            hasta
                    );
        }

        // Reporte general
        return attendance
                .findByFechaBetweenOrderByFechaDesc(
                        desde,
                        hasta
                );
    }

    // Cantidad total de asistencias registradas hoy
    public long countToday() {

        return attendance.countByFecha(
                LocalDate.now()
        );
    }

    // Cantidad de asistencias de un estado específico registradas hoy
    public long countToday(AttendanceStatus status) {

        return attendance.countByFechaAndEstado(
                LocalDate.now(),
                status
        );
    }
}
