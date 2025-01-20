package com.example.jasper.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity

public class Urun {
	 @Id
    private Long id;
    private String urunAdi;
    private Double fiyat;

    // Constructor
    public Urun(String urunAdi, Double fiyat) {
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
    }

    // Getter ve Setter
    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public Double getFiyat() {
        return fiyat;
    }

    public void setFiyat(Double fiyat) {
        this.fiyat = fiyat;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
    
}

