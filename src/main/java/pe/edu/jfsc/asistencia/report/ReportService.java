package pe.edu.jfsc.asistencia.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import pe.edu.jfsc.asistencia.model.Attendance;

@Service
public class ReportService {

    /**
     * Genera un reporte de asistencia en formato Excel.
     */
    public byte[] excel(List<Attendance> rows) throws IOException {

        try (
            Workbook wb = new XSSFWorkbook();
            ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {

            Sheet sheet = wb.createSheet("Asistencia");

            // Encabezados
            Row header = sheet.createRow(0);

            String[] headers = {
                "Fecha",
                "Hora",
                "Código",
                "Estudiante",
                "Grado",
                "Sección",
                "Estado",
                "Observación",
                "Registrado por"
            };

            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Datos
            int rowIndex = 1;

            for (Attendance attendance : rows) {

                Row row = sheet.createRow(rowIndex++);

                Object[] values = {
                    attendance.getFecha().toString(),
                    attendance.getHora().toString(),
                    attendance.getEstudiante().getCodigo(),
                    attendance.getEstudiante().getFullName(),
                    attendance.getSection().getGrado(),
                    attendance.getSection().getNombre(),
                    attendance.getEstado().name(),
                    attendance.getObservacion() == null
                        ? ""
                        : attendance.getObservacion(),
                    attendance.getRegistradoPor()
                };

                for (int i = 0; i < values.length; i++) {
                    row.createCell(i).setCellValue(
                        String.valueOf(values[i])
                    );
                }
            }

            // Ajustar automáticamente el ancho de las columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            wb.write(out);

            return out.toByteArray();
        }
    }

    /**
     * Genera un reporte de asistencia en formato PDF.
     */
    public byte[] pdf(List<Attendance> rows) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(
            PageSize.A4.rotate(),
            24,
            24,
            28,
            24
        );

        PdfWriter.getInstance(document, out);

        document.open();

        // Título
        document.add(
            new Paragraph(
                "SISTEMA WEB DE CONTROL DE ASISTENCIA ESCOLAR",
                FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    16
                )
            )
        );

        // Subtítulo
        document.add(
            new Paragraph(
                "Reporte de asistencia | " +
                "I.E. José Faustino Sánchez Carrión – Huamanga",
                FontFactory.getFont(
                    FontFactory.HELVETICA,
                    10
                )
            )
        );

        // Fecha de generación
        document.add(
            new Paragraph(
                "Generado: " + LocalDateTime.now(),
                FontFactory.getFont(
                    FontFactory.HELVETICA,
                    9
                )
            )
        );

        document.add(Chunk.NEWLINE);

        // Tabla
        PdfPTable table = new PdfPTable(
            new float[] {
                1.3f,
                1.1f,
                1.2f,
                3f,
                1.5f,
                1.2f,
                1.4f
            }
        );

        table.setWidthPercentage(100);

        // Encabezados de la tabla
        String[] headers = {
            "Fecha",
            "Hora",
            "Código",
            "Estudiante",
            "Grado",
            "Sección",
            "Estado"
        };

        for (String header : headers) {
            table.addCell(
                new Phrase(
                    header,
                    FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD,
                        9
                    )
                )
            );
        }

        // Datos de la tabla
        for (Attendance attendance : rows) {

            table.addCell(
                attendance.getFecha().toString()
            );

            table.addCell(
                attendance.getHora().toString()
            );

            table.addCell(
                attendance.getEstudiante().getCodigo()
            );

            table.addCell(
                attendance.getEstudiante().getFullName()
            );

            table.addCell(
                attendance.getSection().getGrado()
            );

            table.addCell(
                attendance.getSection().getNombre()
            );

            table.addCell(
                attendance.getEstado().name()
            );
        }

        document.add(table);

        document.close();

        return out.toByteArray();
    }
}
