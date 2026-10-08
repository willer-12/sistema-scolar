package pe.edu.jfsc.asistencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.jfsc.asistencia.model.Student;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long>{ List<Student> findAllByOrderByApellidosAscNombresAsc(); List<Student> findBySectionIdOrderByApellidosAscNombresAsc(Long sectionId); }
