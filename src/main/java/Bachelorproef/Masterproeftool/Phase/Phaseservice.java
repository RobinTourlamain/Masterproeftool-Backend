package Bachelorproef.Masterproeftool.Phase;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class Phaseservice {

    private final Phaserepository phaserepository;
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public Phaseservice(Phaserepository phaserepository) {
        this.phaserepository = phaserepository;
    }


    public String getCurrentPhase() {
        String s = "";
        Phases p = phaserepository.findById(1).orElse(new Phases());
        if(p.getStartP1() == null){
            return "None";
        }
        LocalDateTime startP1 = p.getStartP1();
        LocalDateTime endP1 = p.getEndP1();
        LocalDateTime endP2 = p.getEndP2();
        LocalDateTime endP3 = p.getEndP3();
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(startP1)||now.isAfter(endP3)){
            s += "None";
        }
        else if(now.isAfter(startP1) && now.isBefore(endP1)){
            s += "P1";
        }
        else if(now.isAfter(endP1) && now.isBefore(endP2)){
            s += "P2";
        }
        else{
            s += "P3";
        }
        return s;
    }

    public void setPhases(Phases p){
        phaserepository.deleteAll();
        phaserepository.save(p);
    }

    public Phases getObject() {
        if(phaserepository.queryById(1) != null){
            return phaserepository.queryById(1);
        }
        else{
            return new Phases();
        }
    }
}
