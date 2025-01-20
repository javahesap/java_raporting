package com.example.jasper.service;

import net.sf.jasperreports.engine.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperjdbcReportService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Connection getConnect() throws SQLException {
		String url = "jdbc:mariadb://127.0.0.1:3306/contact";
		String username = "root";
		String password = "";
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;

	}

	public byte[] createPersonelReport() {

		try {
			// JDBC bağlantısı kurma
			// SQL sorgusunu çalıştırma
			String query = "SELECT * FROM personel";
			ResultSet resultSet = getConnect().createStatement().executeQuery(query);

			// JasperReports için veri kaynağı oluşturma
			JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);

			// Şablon dosyasını yükle
			InputStream reportStream = getClass().getResourceAsStream("/reports/personel_listesijdbc.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

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
	
	
	
	
	public byte[] createparameterPersonelReport(String isim) {
        try {
    
            
 

            // Parametreli SQL sorgusu
            String query = "SELECT * FROM personel WHERE isim = ?";
            PreparedStatement preparedStatement = getConnect().prepareStatement(query);
            preparedStatement.setString(1, isim);  // "isim" parametresini set ediyoruz.

            // Sorguyu çalıştırma
            ResultSet resultSet = preparedStatement.executeQuery();

            // JasperReports için veri kaynağı oluşturma
            JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);

            // Şablon dosyasını yükle
            InputStream reportStream = getClass().getResourceAsStream("/reports/personel_listesijdbcwithparameter.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Parametreleri rapora ilet
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("isim", isim);  // "isim" parametresini rapora gönderiyoruz.

            // Raporu doldur
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // PDF olarak dışa aktar
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new RuntimeException("Rapor oluşturulurken bir hata oluştu.", e);
        }
    }
	
	
	
	
	
	
}
