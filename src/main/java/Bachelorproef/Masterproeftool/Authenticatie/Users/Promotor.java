package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity @Data
public class Promotor extends Gebruiker{
    private String firstname;



    public Promotor(String n, String u, String p, ArrayList r, String fn){
        super( n,  u,  p, r);
        this.firstname = fn;
    }

    public Promotor() {
    }
}


