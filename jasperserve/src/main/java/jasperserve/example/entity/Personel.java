package jasperserve.example.entity;



import org.hibernate.annotations.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity

public class Personel {
	 @Id
    private Long id;
    private String isim;
    private String soyisim;
    private String departman;
    private Double maas;
    
    

    public Personel() {
		
	}

	public Personel(Long id, String isim, String soyisim, String departman, Double maas) {
        this.id = id;
        this.isim = isim;
        this.soyisim = soyisim;
        this.departman = departman;
        this.maas = maas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public Double getMaas() {
        return maas;
    }

    public void setMaas(Double maas) {
        this.maas = maas;
    }
}

