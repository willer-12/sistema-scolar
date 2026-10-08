package pe.edu.jfsc.asistencia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.jfsc.asistencia.model.AppUser;
import java.util.Optional;
public interface AppUserRepository extends JpaRepository<AppUser,Long>{ Optional<AppUser> findByUsername(String username); }
