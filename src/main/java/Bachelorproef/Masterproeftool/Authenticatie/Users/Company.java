package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Rol;
import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;

@Entity @Data
public class Company extends Gebruiker {
    private String contactname;
    private String contactfirstname;
    private String phone;
    private String sector;
    private String address;
    private String website;

    public Company(String name, String username, String password, ArrayList<Rol> rollen, String contactname, String contactfirstname, String phone, String sector, String address, String website) {
        super(name, username, password, rollen);
        this.contactname = contactname;
        this.contactfirstname = contactfirstname;
        this.phone = phone;
        this.sector = sector;
        this.address = address;
        this.website = website;
    }

    public Company() {
    }
}
