package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @NoArgsConstructor @AllArgsConstructor
public class Gebruiker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Rol> rollen = new ArrayList<>();



    public <E> Gebruiker(String name, String username, String password, ArrayList<Rol> rollen) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.rollen = rollen;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Rol> getRollen() {
        return rollen;
    }


    public void setPassword(String encode) {
        this.password = encode;
    }
}
