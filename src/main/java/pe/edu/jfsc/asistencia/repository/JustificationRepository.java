package pe.edu.jfsc.asistencia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.jfsc.asistencia.model.Justification;

public interface JustificationRepository extends JpaRepository<Justification,Long>{ boolean existsByAsistenciaId(Long asistenciaId); }
