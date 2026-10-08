package pe.edu.jfsc.asistencia.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.jfsc.asistencia.model.Student;
import pe.edu.jfsc.asistencia.repository.SectionRepository;
import pe.edu.jfsc.asistencia.repository.StudentRepository;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repo;
    private final SectionRepository sections;

    public StudentController(
            StudentRepository repo,
            SectionRepository sections) {

        this.repo = repo;
        this.sections = sections;
    }

    // Listar estudiantes
    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "students",
                repo.findAllByOrderByApellidosAscNombresAsc()
        );

        return "students/list";
    }

    // Mostrar formulario para crear estudiante
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute(
                "student",
                new Student()
        );

        model.addAttribute(
                "sections",
                sections.findAll()
        );

        return "students/form";
    }

    // Guardar estudiante
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute Student student,
            BindingResult result,
            Model model) {

        // Verificar errores de validación
        if (result.hasErrors()) {

            model.addAttribute(
                    "sections",
                    sections.findAll()
            );

            return "students/form";
        }

        repo.save(student);

        return "redirect:/students?ok";
    }

    // Eliminar estudiante
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        repo.deleteById(id);

        return "redirect:/students?deleted";
    }
}