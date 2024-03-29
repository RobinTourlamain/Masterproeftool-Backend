package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Company;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Onderwerpen")
public class Onderwerp {
    @Id
    @SequenceGenerator(
            name = "Onderwerpsequence",
            sequenceName = "Onderwerpsequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Onderwerpsequence"
    )
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "Doelgroep")
    private String doelgroep;

    @ManyToOne
    @JoinColumn(name = "promotor_id")
    private Gebruiker promotor;
    @Column(name = "email")
    private String email;
    private String phone;
    @Column(name = "capacity")
    private int capacity;
    private String description;

    @ElementCollection
    @Column(name = "disciplines")
    private Collection<String> disciplines = new ArrayList<>();
    @ElementCollection
    @Column(name="trefwoorden")
    private Collection<String> trefwoorden = new ArrayList<>();


    @ManyToMany(mappedBy = "favorites")
    private Collection<Student> likes = new ArrayList<>();

    @ManyToMany(mappedBy = "selection")
    private Collection<Student> selected = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "boosted_student_id")
    private Collection<Student> boosted = new ArrayList<>();

    @OneToMany(mappedBy = "toegewezen")
    private Collection<Student> toegewezen = new ArrayList<>();

    @Column(name = "hideObject")
    private boolean hideObject;

    @ManyToOne
    @JoinTable(
            name = "bedrijfonderwerpen",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "onderwerp_id"))
    private Company bedrijf;

    public Onderwerp(String name, String doelgroep, Gebruiker promotor, String email, String phone, int i, String beschrijving, ArrayList<String> strings, ArrayList<String> strings1, boolean b) {
        this.name = name;
        this.doelgroep = doelgroep;
        this.promotor = promotor;
        this.email = email;
        this.phone = phone;
        this.capacity = i;
        this.description = beschrijving;
        this.disciplines = strings;
        this.trefwoorden = strings1;
        this.hideObject = b;
    }

    public Onderwerp(){}
    public Onderwerp(String name){
        this.name = name;
        this.hideObject = true;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getId(){return this.id;}
    public void setId(int i){this.id = i;}
    public boolean isHideObject() {return hideObject;}
    public void setHideObject(boolean hide) {this.hideObject = hide;}

    public String getDoelgroep() {
        return doelgroep;
    }

    public String getPromotor() {
        return promotor.getName();
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public Collection<String> getDisciplines() {
        return disciplines;
    }

    public Collection<String> getTrefwoorden() {
        return trefwoorden;
    }

    public void setPromotor(Gebruiker promotor) {
        this.promotor = promotor;
    }

    public Collection<Student> getSelection() {
//        return selected.stream().map(Gebruiker::getId).collect(Collectors.toList());
        return selected;
    }

    public Collection<Long> getLikes() {
        return likes.stream().map(Gebruiker::getId).collect(Collectors.toList());
    }


    public Collection<Student> getToegewezen() {
        return toegewezen;
    }

    public List<Long> getBoosted() {
        return boosted.stream().map(Gebruiker::getId).collect(Collectors.toList());
    }

    public void setBoosted(ArrayList<Student> s) {
        boosted = s;
    }
    public Long getBedrijf(){
        if(bedrijf != null){
            return bedrijf.getId();
        }
        else
            return null;
    }

    public void setBedrijf(Company c) {
        bedrijf = c;
    }
}
