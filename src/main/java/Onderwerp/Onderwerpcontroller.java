package Onderwerp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class Onderwerpcontroller {
    private final Onderwerpservice onderwerpservice;
    @Autowired
    public Onderwerpcontroller(Onderwerpservice onderwerpservice){
    this.onderwerpservice = onderwerpservice;
    }

    @GetMapping(path = "/")  //testmapping
    //@ResponseBody                   //String gaat rechtstreeks naar http body
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }
}
