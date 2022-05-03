package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity @Data
public class Student extends Gebruiker {
    private int testint;

    public Student(int i, String n, String u, String p) {
        super( n,  u,  p, new ArrayList<>());
        this.testint = i;
    }

    public Student() {
    }
}
