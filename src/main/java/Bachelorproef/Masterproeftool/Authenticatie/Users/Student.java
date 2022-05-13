package Bachelorproef.Masterproeftool.Authenticatie.Users;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Rol;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import lombok.Data;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private Map<Integer,Onderwerp> selection = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "onderwerp_id")
    private Onderwerp toegewezen;

    public Student(String n, String u, String p, ArrayList<Rol> r, String fn) {
        super( n,  u,  p, r);
        this.firstname = fn;
    }

    public Student() {
    }

    public Student(String name, String username, String password, ArrayList<Rol> rollen, String firstname, String studentnr, int opleiding_id, int campus_id, String address) {
        super(name, username, password, rollen);
        this.firstname = firstname;
        this.studentnr = studentnr;
        this.opleiding_id = opleiding_id;
        this.campus_id = campus_id;
        this.address = address;
    }

    public List<Integer> getFavorites() {
        return favorites.stream().map(Onderwerp::getId).collect(Collectors.toList());
    }

    public Map<Integer, Integer> getSelection() {
        Map<Integer,Integer> m = new HashMap<>();
        if(!selection.isEmpty()){
            for(Integer i : selection.keySet()){
                m.put(i,selection.get(i).getId());
            }
        }
        return m;
    }

    public int getToegewezen() {
        if(toegewezen != null){
            return toegewezen.getId();
        }
        else{
            return -1;
        }
    }

    public void favoriteOnderwerp(Onderwerp o){
        favorites.add(o);
    }
}
