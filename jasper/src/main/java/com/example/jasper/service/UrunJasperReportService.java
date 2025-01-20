package com.example.jasper.service;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class UrunJasperReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] createUrunlerReport() {
        try {
            // SQL sorgusuyla veritabanından veri çekma
            List<Map<String, Object>> urunlerList = jdbcTemplate.queryForList("SELECT urun_adi, fiyat FROM urun");

            // Şablon dosyasını yükle
            InputStream reportStream = getClass().getResourceAsStream("/reports/urunler_listesi.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // DataSource olarak SQL sonucu kullan
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(urunlerList);

            // Parametreler (eğer varsa)
            Map<String, Object> parameters = Map.of("REPORT_TITLE", "Ürünler Raporu");

            // Raporu doldur
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // PDF olarak dışa aktar
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Rapor oluşturulurken bir hata oluştu.", e);
        }
    }
}
