package pe.edu.jfsc.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.jfsc.asistencia.model.Section;
import java.util.List;

public interface SectionRepository extends JpaRepository<Section,Long>{ List<Section> findByTutorUsername(String tutorUsername); }
