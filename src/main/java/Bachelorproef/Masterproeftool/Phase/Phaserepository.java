package Bachelorproef.Masterproeftool.Phase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Phaserepository extends JpaRepository<Phases, Integer> {
    Phases queryById(int i);
}
