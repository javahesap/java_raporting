package com.example.jasper.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jasper.service.JasperReportService;

@RestController
public class ReportController {

    @Autowired
    private JasperReportService jasperReportService;

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
}


