package jasperserve.example.service;


import jasperserve.example.entity.Personel;
import jasperserve.example.repository.PersonelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonelService {

    @Autowired
    private PersonelRepository personelRepository;

    // Tüm personelleri getir
    public List<Personel> getAllPersonel() {
        return personelRepository.findAll();
    }

    // ID ile personel getir
    public Optional<Personel> getPersonelById(Long id) {
        return personelRepository.findById(id);
    }

    // Yeni personel ekle
    public Personel addPersonel(Personel personel) {
        return personelRepository.save(personel);
    }

    // Personel güncelle
    public Personel updatePersonel(Long id, Personel newPersonel) {
        return personelRepository.findById(id).map(personel -> {
            personel.setIsim(newPersonel.getIsim());
            personel.setSoyisim(newPersonel.getSoyisim());
            personel.setDepartman(newPersonel.getDepartman());
            personel.setMaas(newPersonel.getMaas());
            return personelRepository.save(personel);
        }).orElseThrow(() -> new RuntimeException("Personel bulunamadı!"));
    }

    // Personel sil
    public void deletePersonel(Long id) {
        personelRepository.deleteById(id);
    }
}
