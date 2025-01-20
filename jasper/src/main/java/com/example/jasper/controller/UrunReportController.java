package com.example.jasper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jasper.service.UrunJasperReportService;

@RestController
public class UrunReportController {

    @Autowired
    private UrunJasperReportService jasperReportService;

    @GetMapping("/reports/urunler")
    public ResponseEntity<byte[]> generateUrunlerReport() {
        // Raporu oluştur
        byte[] pdfContent = jasperReportService.createUrunlerReport();

        // PDF yanıtını gönder
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "urunler_report.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfContent);
    }
}
