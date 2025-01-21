package jasperserve.example.service;



import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.type.RunDirectionEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperpdfexcelReportService {

    private static final Logger logger = LoggerFactory.getLogger(JasperReportService.class);

    private final DataSource dataSource;

    @Value("${report.template.path:/reports/personel_raporu.jrxml}")
    private String reportTemplatePath;

    public JasperpdfexcelReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] generateReport(String isim, String format) throws Exception {
        logger.info("Rapor oluşturma işlemi başladı. Parametre: isim = {}, format = {}", isim, format);

        // Rapor şablonunu yükle
        InputStream reportStream = getClass().getResourceAsStream(reportTemplatePath);
        if (reportStream == null) {
            logger.error("Rapor şablonu bulunamadı: {}", reportTemplatePath);
            throw new FileNotFoundException("Rapor dosyası bulunamadı: " + reportTemplatePath);
        }

        // Jasper raporunu derle
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        logger.info("Rapor başarıyla derlendi: {}", reportTemplatePath);

        // Parametreleri ayarla
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("isim", isim);

        // Veritabanı bağlantısını al ve raporu doldur
        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Formatı kontrol et ve çıktıyı üret
            if ("pdf".equalsIgnoreCase(format)) {
                return JasperExportManager.exportReportToPdf(jasperPrint);
            } else if ("excel".equalsIgnoreCase(format)) {
                return exportReportToExcel(jasperPrint);
            } else {
                throw new IllegalArgumentException("Geçersiz format: " + format);
            }
        } catch (Exception e) {
            logger.error("Rapor oluşturulurken bir hata meydana geldi.", e);
            throw e;
        }
    }

    private byte[] exportReportToExcel(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(false);
        configuration.setDetectCellType(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setSheetDirection(RunDirectionEnum.LTR);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        return outputStream.toByteArray();
    }
}
