package com.example.jasper.controller;




import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jasper.service.JasperReportService;
import com.example.jasper.service.JasperjdbcReportService;

@RestController
public class ReportController {

    @Autowired
    private JasperReportService jasperReportService;
    
    @Autowired
    private JasperjdbcReportService jasperjdbcReportService;

    @GetMapping("/reports/personel")
    public ResponseEntity<byte[]> generatePersonelReport() {
        // Raporu oluştur
        byte[] pdfContent = jasperReportService.createPersonelReport();

        // PDF yanıtını gönder
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "personel_report.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
    
    

    @GetMapping("/generate-personel-report")
    public ResponseEntity<byte[]> generatejdbcPersonelReport() throws IOException {
        // JasperReportService'yi kullanarak raporu oluştur
        byte[] reportBytes = jasperjdbcReportService.createPersonelReport();

        // Raporu PDF formatında kullanıcıya sun
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=personel_raporu.pdf")
                .body(reportBytes);
    }
    
    
    
 // Parametreli SQL raporu döndüren endpoint
    @GetMapping("/generate-personel-report-parametre")
    public ResponseEntity<byte[]> generatePersonelReport(@RequestParam String isim) throws IOException {
        // JasperReportService'yi kullanarak parametreli raporu oluştur
        byte[] reportBytes = jasperjdbcReportService.createparameterPersonelReport(isim);

        // Raporu PDF formatında kullanıcıya sun
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=personel_raporu.pdf")
                .body(reportBytes);
    }
    
}


