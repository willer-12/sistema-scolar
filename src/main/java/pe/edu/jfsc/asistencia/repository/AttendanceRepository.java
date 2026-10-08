package pe.edu.jfsc.asistencia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.jfsc.asistencia.model.*;
import java.time.LocalDate; 
import java.util.*;

public interface AttendanceRepository extends JpaRepository<Attendance,Long>{
    Optional<Attendance> findByFechaAndEstudianteId(LocalDate fecha, Long estudianteId);
    List<Attendance> findByFechaBetweenOrderByFechaDesc(LocalDate desde, LocalDate hasta);
    List<Attendance> findByEstudianteSectionIdAndFechaBetweenOrderByFechaDesc(Long sectionId, LocalDate desde, LocalDate hasta);
    List<Attendance> findByEstudianteIdAndFechaBetweenOrderByFechaDesc(Long studentId, LocalDate desde, LocalDate hasta);
    long countByFecha(LocalDate fecha);
    long countByFechaAndEstado(LocalDate fecha, AttendanceStatus estado);
}
