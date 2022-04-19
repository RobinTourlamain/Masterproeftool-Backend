package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
    @ManyToMany(mappedBy = "favorites")
    private Collection<Gebruiker> likes = new ArrayList<>();

    public Onderwerp(){}
    public Onderwerp(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getId(){return this.id;}
    public void setId(int i){this.id = i;}
}
