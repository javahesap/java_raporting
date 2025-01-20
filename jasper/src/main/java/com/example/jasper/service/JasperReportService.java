package com.example.jasper.service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import com.example.jasper.model.Personel;
import com.example.jasper.repository.PersonelRepository;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class JasperReportService {

    @Autowired
    private PersonelRepository personelRepository;

    public byte[] createPersonelReport() {
        try {
            // Veritabanından personel listesi al
            List<Personel> personelList = personelRepository.findAll();

            // Şablon dosyasını yükle
            InputStream reportStream = getClass().getResourceAsStream("/reports/personel_listesi.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // DataSource olarak Personel listesi kullan
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(personelList);

            // Parametreler (eğer varsa)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("REPORT_TITLE", "Personel Raporu");

            // Raporu doldur
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // PDF olarak dışa aktar
            return JasperExportManager.exportReportToPdf(jasperPrint);
          

        } catch (Exception e) {
            throw new RuntimeException("Rapor oluşturulurken bir hata oluştu.", e);
        }
    }

}


