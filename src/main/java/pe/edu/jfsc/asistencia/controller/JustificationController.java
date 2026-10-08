package pe.edu.jfsc.asistencia.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.jfsc.asistencia.model.AttendanceStatus;
import pe.edu.jfsc.asistencia.model.Justification;
import pe.edu.jfsc.asistencia.repository.AttendanceRepository;
import pe.edu.jfsc.asistencia.repository.JustificationRepository;

@Controller
@RequestMapping("/justifications")
public class JustificationController {

    private final JustificationRepository repo;
    private final AttendanceRepository attendance;

    public JustificationController(
            JustificationRepository repo,
            AttendanceRepository attendance) {

        this.repo = repo;
        this.attendance = attendance;
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute(
                "items",
                repo.findAll()
        );

        model.addAttribute(
                "attendances",
                attendance.findAll()
                        .stream()
                        .filter(a -> a.getEstado() != AttendanceStatus.PRESENTE)
                        .toList()
        );

        return "justifications/list";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam Long asistenciaId,
            @RequestParam String motivo,
            @RequestParam(required = false) String documento,
            Authentication auth) {

        // Validar que el motivo haya sido ingresado
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "Motivo obligatorio"
            );
        }

        // Buscar la asistencia
        var asistencia = attendance.findById(asistenciaId)
                .orElseThrow();

        // Verificar si ya existe una justificación
        if (repo.existsByAsistenciaId(asistenciaId)) {
            throw new IllegalArgumentException(
                    "Esta asistencia ya tiene justificación"
            );
        }

        // Registrar la justificación
        repo.save(
                new Justification(
                        LocalDateTime.now(),
                        motivo,
                        documento,
                        asistencia,
                        auth.getName()
                )
        );

        return "redirect:/justifications?ok";
    }
}