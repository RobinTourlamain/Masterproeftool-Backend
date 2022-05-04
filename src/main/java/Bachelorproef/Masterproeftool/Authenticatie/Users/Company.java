package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;

@Entity @Data
public class Company extends Gebruiker {
    private String contactname;
    private String contactfirstname;
    private String phone;
    private String sector;
    private String address;
    private String website;
}
