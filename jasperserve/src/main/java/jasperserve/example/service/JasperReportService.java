package jasperserve.example.service;



import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    @Autowired
    private DataSource dataSource;

    public byte[] generateReport(String isim) throws Exception {
        // Rapor şablonunu yükle
        InputStream reportStream = getClass().getResourceAsStream("/reports/personel_raporu.jrxml");
        if (reportStream == null) {
            throw new RuntimeException("Rapor dosyası bulunamadı!");
        }

        // Jasper raporunu derle
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Parametreleri ayarla
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("isim", isim);

        // Veritabanı bağlantısını al
        try (Connection connection = dataSource.getConnection()) {
            // Raporu doldur
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // PDF çıktısını oluştur
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}

