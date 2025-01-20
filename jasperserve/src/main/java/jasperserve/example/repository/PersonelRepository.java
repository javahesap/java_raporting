package jasperserve.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import jasperserve.example.entity.Personel;

public interface PersonelRepository extends JpaRepository<Personel, Long> {
    // Ek sorgular gerektiğinde burada tanımlayabilirsiniz.
}

