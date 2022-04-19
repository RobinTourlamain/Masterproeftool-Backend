package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Gebruiker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Rol> rollen = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "Onderwerplikes",
            joinColumns = @JoinColumn(name = "Gebruiker_id"),
            inverseJoinColumns = @JoinColumn(name = "onderwerp_id"))
    private Collection<Onderwerp> favorites = new ArrayList<>();

    public <E> Gebruiker(String name, String username, String password, ArrayList<Rol> rollen) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.rollen = rollen;
    }
}
