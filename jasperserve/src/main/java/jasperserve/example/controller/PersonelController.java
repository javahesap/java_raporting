package jasperserve.example.controller;



import jasperserve.example.entity.Personel;
import jasperserve.example.service.PersonelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personel")
public class PersonelController {

    @Autowired
    private PersonelService personelService;

    // Tüm personelleri getir
    @GetMapping
    public List<Personel> getAllPersonel() {
        return personelService.getAllPersonel();
    }

    // ID ile personel getir
    @GetMapping("/{id}")
    public ResponseEntity<Personel> getPersonelById(@PathVariable Long id) {
        return personelService.getPersonelById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Yeni personel ekle
    @PostMapping
    public Personel addPersonel(@RequestBody Personel personel) {
        return personelService.addPersonel(personel);
    }

    // Personel güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Personel> updatePersonel(@PathVariable Long id, @RequestBody Personel personel) {
        try {
            Personel updatedPersonel = personelService.updatePersonel(id, personel);
            return ResponseEntity.ok(updatedPersonel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Personel sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonel(@PathVariable Long id) {
        personelService.deletePersonel(id);
        return ResponseEntity.noContent().build();
    }
}
