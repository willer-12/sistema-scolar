package pe.edu.jfsc.asistencia.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.jfsc.asistencia.model.AppUser;
import pe.edu.jfsc.asistencia.model.Role;
import pe.edu.jfsc.asistencia.repository.AppUserRepository;

@Service
public class UserService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public List<AppUser> all() {
        return repo.findAll();
    }

    public AppUser create(String username, String password, String nombres, Role role) {

        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        return repo.save(
            new AppUser(
                username,
                encoder.encode(password),
                nombres,
                role
            )
        );
    }
}