package ma.emsi.patientsmvc.repositories;


import ma.emsi.patientsmvc.entities.Medecin;
import ma.emsi.patientsmvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin, Long > {
    //Medecin findByNom(String nom);
    Page<Medecin> findByNomContains(String kw, Pageable pageable);
}
