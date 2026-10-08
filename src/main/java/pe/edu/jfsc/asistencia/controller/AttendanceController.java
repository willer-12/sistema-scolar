package pe.edu.jfsc.asistencia.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.jfsc.asistencia.model.AttendanceStatus;
import pe.edu.jfsc.asistencia.model.Section;
import pe.edu.jfsc.asistencia.repository.AppUserRepository;
import pe.edu.jfsc.asistencia.repository.SectionRepository;
import pe.edu.jfsc.asistencia.service.AttendanceService;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final SectionRepository sections;
    private final AttendanceService service;
    private final AppUserRepository users;

    public AttendanceController(
            SectionRepository sections,
            AttendanceService service,
            AppUserRepository users) {

        this.sections = sections;
        this.service = service;
        this.users = users;
    }

    @GetMapping
    public String form(
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) String fecha,
            Model model,
            Authentication auth) {

        // Verificar si el usuario es administrador
        boolean admin = auth.getAuthorities()
                .stream()
                .anyMatch(
                        authority -> authority
                                .getAuthority()
                                .equals("ROLE_ADMINISTRADOR")
                );

        // Obtener las secciones según el rol del usuario
        List<Section> sectionsList = admin
                ? sections.findAll()
                : sections.findByTutorUsername(auth.getName());

        // Seleccionar la primera sección automáticamente
        if (sectionId == null && !sectionsList.isEmpty()) {
            sectionId = sectionsList.get(0).getId();
        }

        // Obtener la fecha seleccionada o utilizar la fecha actual
        LocalDate date = fecha == null
                ? LocalDate.now()
                : LocalDate.parse(fecha);

        // Enviar información a la vista
        model.addAttribute("sections", sectionsList);
        model.addAttribute("selectedSection", sectionId);
        model.addAttribute("fecha", date);

        model.addAttribute(
                "students",
                sectionId == null
                        ? List.of()
                        : service.studentsFor(sectionId)
        );

        model.addAttribute(
                "statuses",
                AttendanceStatus.values()
        );

        model.addAttribute(
                "title",
                "Registro de asistencia"
        );

        return "attendance/form";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam Long sectionId,
            @RequestParam LocalDate fecha,
            @RequestParam Map<String, String> allParams,
            Authentication auth) {

        Map<Long, String> estados = new HashMap<>();
        Map<Long, String> observaciones = new HashMap<>();

        // Procesar los parámetros enviados desde el formulario
        allParams.forEach((key, value) -> {

            if (key.startsWith("estado_")) {

                Long studentId = Long.valueOf(
                        key.substring(7)
                );

                estados.put(studentId, value);

            } else if (key.startsWith("obs_")) {

                Long studentId = Long.valueOf(
                        key.substring(4)
                );

                observaciones.put(studentId, value);
            }
        });

        // Verificar si el usuario es administrador
        boolean admin = auth.getAuthorities()
                .stream()
                .anyMatch(
                        authority -> authority
                                .getAuthority()
                                .equals("ROLE_ADMINISTRADOR")
                );

        // Validar que el usuario tenga acceso a la sección
        if (!admin) {

            boolean sectionAssigned = sections
                    .findByTutorUsername(auth.getName())
                    .stream()
                    .anyMatch(
                            section -> section
                                    .getId()
                                    .equals(sectionId)
                    );

            if (!sectionAssigned) {
                throw new AccessDeniedException(
                        "Sección no asignada"
                );
            }
        }

        // Guardar las asistencias
        service.saveBulk(
                sectionId,
                fecha,
                estados,
                observaciones,
                auth.getName()
        );

        // Regresar al formulario
        return "redirect:/attendance?sectionId="
                + sectionId
                + "&fecha="
                + fecha
                + "&ok";
    }
}
