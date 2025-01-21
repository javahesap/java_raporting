package jasperserve.example.controller;



import jasperserve.example.service.JasperReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JasperReportController {

    @Autowired
    private JasperReportService jasperReportService;

    @GetMapping("/api/rapor")
    public ResponseEntity<byte[]> getReport(@RequestParam String isim) {
        try {
            // PDF oluştur
            byte[] pdfData = jasperReportService.generateReport(isim);

            // HTTP cevabı olarak döndür
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=personel_raporu.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
