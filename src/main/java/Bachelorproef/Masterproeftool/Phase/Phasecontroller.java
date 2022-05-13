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

    @PostMapping("/setstart")
    public Phases setStart(@RequestBody LocalDateTime t){
        return phaseservice.setStart(t);
    }

    @PostMapping("/setend1")
    public Phases setEnd1(@RequestBody LocalDateTime t){
        return phaseservice.setEnd1(t);
    }
    @PostMapping("/setend2")
    public Phases setEnd2(@RequestBody LocalDateTime t){
        return phaseservice.setEnd2(t);
    }
    @PostMapping("/setend3")
    public Phases setEnd3(@RequestBody LocalDateTime t){
        return phaseservice.setEnd3(t);
    }
    @PostMapping("/setend4")
    public Phases setEnd4(@RequestBody LocalDateTime t){
        return phaseservice.setEnd4(t);
    }
    @PostMapping("/setend5")
    public Phases setEnd5(@RequestBody LocalDateTime t){
        return phaseservice.setEnd5(t);
    }
}
