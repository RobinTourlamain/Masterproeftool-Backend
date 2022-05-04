package Bachelorproef.Masterproeftool.Onderwerp;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Onderwerprepository extends JpaRepository<Onderwerp, Integer> {
    List<Onderwerp> findAll();

    Onderwerp queryById(int id);
}
