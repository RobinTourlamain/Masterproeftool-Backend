package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import lombok.Data;

import javax.persistence.Entity;

@Entity @Data
public class Promotor extends Gebruiker{
    private String firstname;
}