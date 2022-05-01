package Bachelorproef.Masterproeftool.Onderwerp;

import Bachelorproef.Masterproeftool.Authenticatie.Gebruiker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController //controller en responsebody ineen
public class Onderwerpcontroller {
    private final Onderwerpservice onderwerpservice;
    @Autowired
    public Onderwerpcontroller(Onderwerpservice onderwerpservice){
    this.onderwerpservice = onderwerpservice;
    }

    @GetMapping(path = "/hello")  //testmapping
    //@ResponseBody                   //String gaat rechtstreeks naar http body
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }

    @GetMapping(path = "/onderwerpen")
    public List<Onderwerp> getOnderwerpen(@RequestParam Optional<String> sortBy){
        return onderwerpservice.getAlleOnderwerpen(sortBy.orElse("id"));
    }
    @GetMapping(path = "/onderwerpen/{id}")
    Onderwerp getOnderwerpById(@PathVariable int id){
        return onderwerpservice.getOnderwerpById(id);
    }

    @PutMapping(path = "/onderwerpen/{id}")
    Onderwerp vervangOnderwerp(@RequestBody Onderwerp temponderwerp, @PathVariable int id){
        return onderwerpservice.vervangOnderwerp(temponderwerp, id);
    }
    @PostMapping(path = "/addonderwerp/{promotorid}") //voeg onderwerp toe
    Onderwerp addOnderwerp(@RequestBody Onderwerp tempOnderwerp,@PathVariable int promotorid){
        return onderwerpservice.voegOnderwerpToe(tempOnderwerp, promotorid);
    }
    @DeleteMapping(path = "/onderwerpen/{id}")
    void deleteOnderwerp(@PathVariable int id) {
        onderwerpservice.deleteOnderwerp(id);
    }
    @PostMapping(path = "/voegToe/{id}")
    public Onderwerp voegToe(@PathVariable int id) {
        Onderwerp temp = onderwerpservice.getOnderwerpById(id);
        temp.setHideObject(false);
        temp = onderwerpservice.updateHideObject(temp);
        return temp;
    }
    @DeleteMapping(path = "/verwijder/{id}")
    public Onderwerp verwijder(@PathVariable int id) {
        Onderwerp temp = onderwerpservice.getOnderwerpById(id);
        temp.setHideObject(true);
        temp = onderwerpservice.updateHideObject(temp);
        return temp;
    }
}
