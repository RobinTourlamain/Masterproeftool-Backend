package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Rol;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data
public class Student extends Gebruiker {
    private String firstname;
    private String studentnr;
    private int opleiding_id;
    private int campus_id;
    private String address;

    @ManyToMany
    @JoinTable(
            name = "Onderwerplikes",
            joinColumns = @JoinColumn(name = "Gebruiker_id"),
            inverseJoinColumns = @JoinColumn(name = "onderwerp_id"))
    private Collection<Onderwerp> favorites = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "OnderwerpSelection",
            joinColumns = @JoinColumn(name = "Gebruiker_id"),
            inverseJoinColumns = @JoinColumn(name = "onderwerp_id"))
    private Collection<Onderwerp> selection = new ArrayList<>();

    public Student(String n, String u, String p, ArrayList<Rol> r, String fn) {
        super( n,  u,  p, r);
        this.firstname = fn;
    }

    public Student() {
    }

    public Collection<Onderwerp> getFavorites() {
        return favorites;
    }

    public Collection<Onderwerp> getSelection() {
        return selection;
    }

    public void favoriteOnderwerp(Onderwerp o){
        favorites.add(o);
    }
}
