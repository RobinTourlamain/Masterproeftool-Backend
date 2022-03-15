package Bachelorproef.Masterproeftool.Authenticatie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Rolrepository extends JpaRepository<Rol, Long> {
    Rol findByName(String name);
}
