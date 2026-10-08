package pe.edu.jfsc.asistencia.controller;

import java.time.LocalDate;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.jfsc.asistencia.model.AttendanceStatus;
import pe.edu.jfsc.asistencia.repository.SectionRepository;
import pe.edu.jfsc.asistencia.repository.StudentRepository;
import pe.edu.jfsc.asistencia.report.ReportService;
import pe.edu.jfsc.asistencia.service.AttendanceService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final AttendanceService service;
    private final SectionRepository sections;
    private final StudentRepository students;
    private final ReportService reports;

    public ReportController(
            AttendanceService service,
            SectionRepository sections,
            StudentRepository students,
            ReportService reports) {

        this.service = service;
        this.sections = sections;
        this.students = students;
        this.reports = reports;
    }

    // Convertir una fecha String a LocalDate
    private LocalDate parseDate(
            String date,
            LocalDate fallback) {

        return date == null || date.isBlank()
                ? fallback
                : LocalDate.parse(date);
    }

    // Mostrar reporte de asistencias
    @GetMapping
    public String report(
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long studentId,
            Model model) {

        LocalDate fechaInicio = parseDate(
                desde,
                LocalDate.now().withDayOfMonth(1)
        );

        LocalDate fechaFin = parseDate(
                hasta,
                LocalDate.now()
        );

        var rows = service.report(
                fechaInicio,
                fechaFin,
                sectionId,
                studentId
        );

        // Fechas y filtros seleccionados
        model.addAttribute("desde", fechaInicio);
        model.addAttribute("hasta", fechaFin);
        model.addAttribute("sectionId", sectionId);
        model.addAttribute("studentId", studentId);

        // Datos para los filtros
        model.addAttribute(
                "sections",
                sections.findAll()
        );

        model.addAttribute(
                "students",
                students.findAllByOrderByApellidosAscNombresAsc()
        );

        // Registros de asistencia
        model.addAttribute("rows", rows);

        // Totales
        model.addAttribute(
                "total",
                rows.size()
        );

        model.addAttribute(
                "present",
                rows.stream()
                        .filter(row ->
                                row.getEstado() == AttendanceStatus.PRESENTE
                        )
                        .count()
        );

        model.addAttribute(
                "late",
                rows.stream()
                        .filter(row ->
                                row.getEstado() == AttendanceStatus.TARDANZA
                        )
                        .count()
        );

        model.addAttribute(
                "absent",
                rows.stream()
                        .filter(row ->
                                row.getEstado() == AttendanceStatus.FALTA
                        )
                        .count()
        );

        return "reports/report";
    }

    // Exportar reporte a Excel
    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> excel(
            @RequestParam String desde,
            @RequestParam String hasta,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long studentId)
            throws Exception {

        var rows = service.report(
                LocalDate.parse(desde),
                LocalDate.parse(hasta),
                sectionId,
                studentId
        );

        byte[] excel = reports.excel(rows);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=asistencia.xlsx"
                )
                .contentType(
                        MediaType.APPLICATION_OCTET_STREAM
                )
                .body(excel);
    }

    // Exportar reporte a PDF
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> pdf(
            @RequestParam String desde,
            @RequestParam String hasta,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long studentId)
            throws Exception {

        var rows = service.report(
                LocalDate.parse(desde),
                LocalDate.parse(hasta),
                sectionId,
                studentId
        );

        byte[] pdf = reports.pdf(rows);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=asistencia.pdf"
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(pdf);
    }
}