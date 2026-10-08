package pe.edu.jfsc.asistencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.jfsc.asistencia.model.Role;
import pe.edu.jfsc.asistencia.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // Listar usuarios
    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "users",
                service.all()
        );

        model.addAttribute(
                "roles",
                Role.values()
        );

        return "users/list";
    }

    // Crear usuario
    @PostMapping("/save")
    public String save(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nombres,
            @RequestParam Role role) {

        service.create(
                username,
                password,
                nombres,
                role
        );

        return "redirect:/users?ok";
    }
}