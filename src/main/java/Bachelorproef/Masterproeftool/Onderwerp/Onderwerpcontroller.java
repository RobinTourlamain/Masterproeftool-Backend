package Bachelorproef.Masterproeftool.Onderwerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Onderwerp> getOnderwerpen(){
        return onderwerpservice.getAlleOnderwerpen();
    }
    @GetMapping(path = "/onderwerpen/{id}")
    Onderwerp getOnderwerpById(@PathVariable int id){
        return onderwerpservice.getOnderwerpById(id);
    }
    @PutMapping(path = "/onderwerpen/{id}")
    Onderwerp vervangOnderwerp(@RequestBody Onderwerp temponderwerp, @PathVariable int id){
        return onderwerpservice.vervangOnderwerp(temponderwerp, id);
    }
    @PostMapping(path = "/addonderwerp") //voeg onderwerp toe
    Onderwerp addOnderwerp(@RequestBody Onderwerp tempOnderwerp){
        return onderwerpservice.voegOnderwerpToe(tempOnderwerp);
    }
    @DeleteMapping(path = "/onderwerpen/{id}")
    void deleteOnderwerp(@PathVariable int id) {
        onderwerpservice.deleteOnderwerp(id);
    }
}
