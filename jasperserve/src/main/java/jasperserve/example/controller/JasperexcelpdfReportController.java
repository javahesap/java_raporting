package jasperserve.example.controller;
import jasperserve.example.service.JasperpdfexcelReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class JasperexcelpdfReportController {

    private static final Logger logger = LoggerFactory.getLogger(JasperexcelpdfReportController.class);

    private final JasperpdfexcelReportService jasperReportService;

    public JasperexcelpdfReportController(JasperpdfexcelReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }


    @GetMapping("/api/raporexclpef")
    public ResponseEntity<byte[]> getReport(@RequestParam String isim, @RequestParam String format) {
        try {
            byte[] reportData = jasperReportService.generateReport(isim, format.toLowerCase());

            String contentType;
            String extension;

            if ("pdf".equalsIgnoreCase(format)) {
                contentType = MediaType.APPLICATION_PDF_VALUE;
                extension = "pdf";
            } else if ("excel".equalsIgnoreCase(format)) {
                contentType = "application/vnd.ms-excel";
                extension = "xls";
            } else {
                // Geçersiz format durumunda bir hata atılmalı
                throw new IllegalArgumentException("Geçersiz format: " + format);
            }

            String filename = String.format("%s_raporu.%s", isim, extension);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(reportData);

        } catch (IllegalArgumentException e) {
            // Geçersiz format için bir hata yanıtı
            return ResponseEntity.badRequest()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
                    .body(e.getMessage().getBytes());
        } catch (Exception e) {
            // Genel hata durumu
            return ResponseEntity.internalServerError().build();
        }
    }

}


