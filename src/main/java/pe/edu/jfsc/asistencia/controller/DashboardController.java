package pe.edu.jfsc.asistencia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pe.edu.jfsc.asistencia.repository.SectionRepository;
import pe.edu.jfsc.asistencia.repository.StudentRepository;
import pe.edu.jfsc.asistencia.service.DashboardService;

@Controller
public class DashboardController {

    private final DashboardService dash;
    private final StudentRepository students;
    private final SectionRepository sections;

    public DashboardController(
            DashboardService dash,
            StudentRepository students,
            SectionRepository sections) {

        this.dash = dash;
        this.students = students;
        this.sections = sections;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {

        model.addAttribute(
                "todayTotal",
                dash.totalToday()
        );

        model.addAttribute(
                "present",
                dash.presentToday()
        );

        model.addAttribute(
                "late",
                dash.lateToday()
        );

        model.addAttribute(
                "absent",
                dash.absentToday()
        );

        model.addAttribute(
                "studentCount",
                students.count()
        );

        model.addAttribute(
                "sectionCount",
                sections.count()
        );

        return "dashboard";
    }
}