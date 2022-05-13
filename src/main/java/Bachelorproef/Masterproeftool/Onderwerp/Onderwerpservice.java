package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import Bachelorproef.Masterproeftool.Authenticatie.Gebruikerservice;
import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class Onderwerpservice {

    private final Onderwerprepository onderwerprepository;
    private final Gebruikerservice gebruikerservice;
    @Autowired
    public Onderwerpservice(Onderwerprepository onderwerprepository, Gebruikerservice gebruikerservice){
        this.onderwerprepository = onderwerprepository;
        this.gebruikerservice = gebruikerservice;
    }

    public Onderwerp voegOnderwerpToe(Onderwerp tempOnderwerp, int promotorid) {
        if(gebruikerservice.findById(promotorid).isEmpty()){
            throw new Promotornotfoundexception(promotorid);
        }
        else{
            tempOnderwerp.setPromotor(gebruikerservice.findById(promotorid).orElse(gebruikerservice.findByUsername("Admin")));
            return onderwerprepository.save(tempOnderwerp);
        }
    }

    public Onderwerp voegOnderwerpToeKantEnKlaar(Onderwerp temp){
        return onderwerprepository.save(temp);
    }

    public Onderwerp getOnderwerpById(int id) {
        return onderwerprepository.findById(id).orElseThrow(() -> new Onderwerpnotfoundexception(id));
    }

    public List getOnderwerpenById(ArrayList ids){
        return onderwerprepository.findAllById(ids);
    }

    public Onderwerp vervangOnderwerp(Onderwerp temponderwerp, int id) {
        return onderwerprepository.findById(id)
                .map(onderwerp -> {
                    onderwerp.setName(temponderwerp.getName());
                    return onderwerprepository.save(onderwerp);
                })
                .orElseGet(() -> {
                    temponderwerp.setId(id);
                    return onderwerprepository.save(temponderwerp);
                });
    }

    public void deleteOnderwerp(int id) {
        onderwerprepository.deleteById(id);
    }

    public List<Onderwerp> getAlleOnderwerpen(String id) {
        return onderwerprepository.findAll(Sort.by(Sort.Direction.ASC, id));
    }

    public Onderwerp updateHideObject(Onderwerp onderwerp) {
        return onderwerprepository.save(onderwerp);
    }

    public ArrayList<Student> getSelection(int id) {
        Onderwerp o = onderwerprepository.queryById(id);
        ArrayList<Student> a = new ArrayList<>();
        if(o != null){
//            for(long l : o.getSelection()){
//                Student s;
//                s = gebruikerservice.findStudentById(l);
//                a.add(s);
//            }
            a.addAll(o.getSelection());
        }
        return a;
    }

    public Onderwerp boostStudent(int oid, long sid) {
        Onderwerp o = onderwerprepository.queryById(oid);
        o.setBoosted(gebruikerservice.findStudentById(sid));
        onderwerprepository.save(o);
        return o;
    }

    public Student wijsToe(int oid, long sid) {
        Onderwerp o = onderwerprepository.queryById(oid);
        if(o.getToegewezen().size() < o.getCapacity()){
            return gebruikerservice.wijsToe(o,sid);
        }
        else{
            throw new Onderwerpovercapacityexception();
        }
    }

    public ArrayList<ArrayList<Student>> getAllSelection() {
        ArrayList<ArrayList<Student>> a = new ArrayList<>();
        for(Onderwerp o : onderwerprepository.findAll()){
            ArrayList<Student> s = new ArrayList<>();
//            for(Long l : o.getSelection()){
//                s.add(gebruikerservice.findStudentById(l));
//            }
            s.addAll(o.getSelection());
            a.add(s);
        }
        return a;
    }

    public void deleteFavoriteOnderwerp(Student g, Onderwerp o) {
        g.getFavorites().remove(o.getId());
        ArrayList<Onderwerp> a = new ArrayList<>();
        for(Integer i : g.getFavorites()){
            a.add(onderwerprepository.getById(i));
        }
        g.setFavorites(a);
        gebruikerservice.saveGebruiker(g);
    }
}
