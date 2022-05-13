package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Rol;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data
public class Company extends Gebruiker {
    private String contactname;
    private String contactfirstname;
    private String phone;
    private String sector;
    private String address;
    private String website;

    @OneToMany(mappedBy = "bedrijf")
    private Collection<Onderwerp> onderwerpen = new ArrayList<>();

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
