package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Gebruikerrepository extends JpaRepository<Gebruiker, Long> {
    Gebruiker findByUsername(String username);

    List<Gebruiker> findByRollenContaining(Rol r);

    Student findStudentByUsername(String username);
}
