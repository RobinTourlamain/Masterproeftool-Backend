package Bachelorproef.Masterproeftool.Authenticatie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    public Rol(String name) {
        this.name = name;
        if (name.equalsIgnoreCase("Admin")) {
            id = 1;
        }
        else if (name.equalsIgnoreCase("Student")) {
            id = 2;
        }
        else if (name.equalsIgnoreCase("Coordinator")) {
            id = 3;
        }
        else if (name.equalsIgnoreCase("Bedrijf")) {
            id = 4;
        }
        else if (name.equalsIgnoreCase("Promotor")) {
            id = 5;
        }
    }
}
