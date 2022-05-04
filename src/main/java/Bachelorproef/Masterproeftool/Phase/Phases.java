package Bachelorproef.Masterproeftool.Phase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity @Data
public class Phases {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    private java.time.LocalDateTime startP1;
    private java.time.LocalDateTime endP1;
    private java.time.LocalDateTime endP2;
    private java.time.LocalDateTime endP3;


    public Phases(LocalDateTime s,LocalDateTime e1,LocalDateTime e2,LocalDateTime e3){
        this.id = 1;
        this.startP1 = s;
        this.endP1 = e1;
        this.endP2 = e2;
        this.endP3 = e3;
    }

    public Phases() {
        this.id = 1;
    }

    public Phases(LocalDateTime t) {
        this.id = 1;
        this.startP1 = t;
    }

    public Phases(int id, LocalDateTime startP1, LocalDateTime endP1, LocalDateTime endP2, LocalDateTime endP3) {
        this.id = id;
        this.startP1 = startP1;
        this.endP1 = endP1;
        this.endP2 = endP2;
        this.endP3 = endP3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
