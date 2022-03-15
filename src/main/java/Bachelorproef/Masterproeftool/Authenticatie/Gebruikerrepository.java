package Bachelorproef.Masterproeftool.Authenticatie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Gebruikerrepository extends JpaRepository<Gebruiker, Long> {
    Gebruiker findByUsername(String username);
}
