package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Coordinator extends Gebruiker {
    private String firstname;
    private String phone;
    private int opleiding_id;
    private int campus_id;
}
