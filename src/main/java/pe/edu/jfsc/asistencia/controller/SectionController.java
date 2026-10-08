package pe.edu.jfsc.asistencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.jfsc.asistencia.model.Role;
import pe.edu.jfsc.asistencia.model.Section;
import pe.edu.jfsc.asistencia.repository.AppUserRepository;
import pe.edu.jfsc.asistencia.repository.SectionRepository;

@Controller
@RequestMapping("/sections")
public class SectionController {

    private final SectionRepository repo;
    private final AppUserRepository users;

    public SectionController(
            SectionRepository repo,
            AppUserRepository users) {

        this.repo = repo;
        this.users = users;
    }

    // Listar todas las secciones
    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "sections",
                repo.findAll()
        );

        return "sections/list";
    }

    // Mostrar formulario para crear una sección
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute(
                "section",
                new Section()
        );

        model.addAttribute(
                "teachers",
                users.findAll()
                        .stream()
                        .filter(user -> user.getRole() == Role.DOCENTE)
                        .toList()
        );

        return "sections/form";
    }

    // Guardar sección
    @PostMapping("/save")
    public String save(@ModelAttribute Section section) {

        repo.save(section);

        return "redirect:/sections?ok";
    }

    // Eliminar sección
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        repo.deleteById(id);

        return "redirect:/sections?deleted";
    }
}