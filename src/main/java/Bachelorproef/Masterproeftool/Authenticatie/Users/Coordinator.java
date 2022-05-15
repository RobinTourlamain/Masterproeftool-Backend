package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity
@Data
public class Coordinator extends Gebruiker {
    private String firstname;
    private String phone;
    private int opleiding_id;
    private int campus_id;

    public Coordinator(String n, String u, String p, ArrayList r, String fn,String ph,int o,int c){
        super( n,  u,  p, r);
        this.firstname = fn;
        this.phone = ph;
        this.opleiding_id = o;
        this.campus_id = c;

    }

    public Coordinator() {

    }
}
