package Bachelorproef.Masterproeftool.Phase;

import org.apache.tomcat.jni.Local;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/phase")
public class Phasecontroller {
    private final Phaseservice phaseservice;

    public Phasecontroller(Phaseservice phaseservice) {
        this.phaseservice = phaseservice;
    }

    @GetMapping("/getcurrent")
    public String getCurrentPhase(){
        return phaseservice.getCurrentPhase();
    }

    @GetMapping("/getobject")
    public Phases getObject(){
        return phaseservice.getObject();
    }

    @PostMapping("/setcurrent")
    public void setPhases(@RequestBody Phases p){
        phaseservice.setPhases(p);
    }
}
