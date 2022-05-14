package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Users.Student;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerp;
import Bachelorproef.Masterproeftool.Onderwerp.Onderwerpservice;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController @RequiredArgsConstructor
@RequestMapping("/auth")
public class Gebruikercontroller {
    private static final Logger log = LoggerFactory.getLogger(Gebruikercontroller.class);
    private final Gebruikerservice gebruikerservice;
    private final Rolservice rolservice;
    private final Onderwerpservice onderwerpservice;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/gebruikers")
    public ResponseEntity<List<Gebruiker>> getGebruikers(){
        return ResponseEntity.ok().body(gebruikerservice.findAllGebruikers());
    }

    @GetMapping("/studenten/naam/{id}")
    public String getName(@PathVariable int id){
        String s = "";
        s += gebruikerservice.findStudentById(id).getFirstname() + " " + gebruikerservice.findStudentById(id).getName();
        return s;
    }

    @PostMapping("/gebruikers/save")
    public ResponseEntity<Gebruiker> saveGebruiker(@RequestBody Gebruiker gebruiker){
        return ResponseEntity.ok().body(gebruikerservice.saveNewGebruiker(gebruiker));
    }

    @PostMapping("/gebruikers/savestudent")
    public ResponseEntity<Gebruiker> saveGebruiker(@RequestBody Student gebruiker){
        return ResponseEntity.ok().body(gebruikerservice.saveNewGebruiker(gebruiker));
    }

    @PostMapping("/rollen/save")
    public ResponseEntity<Rol> saveRol(@RequestBody Rol rol){
        return ResponseEntity.ok().body(rolservice.saveRol(rol));
    }

    @PostMapping("/rollen/addtouser")
    public ResponseEntity<?> addRolToGebruiker(@RequestBody RoleToUserForm form){
        rolservice.addRolToGebruiker(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/favorieten")
    public ResponseEntity<Collection<Onderwerp>> getFavorites(Principal principal){
        Student g = gebruikerservice.findStudentByUsername(principal.getName());
        Collection<Onderwerp> c = new ArrayList<>();
        for(Integer i : g.getFavorites()){
            c.add(onderwerpservice.getOnderwerpById(i));
        }
        return ResponseEntity.ok().body(c);
    }

    @PostMapping(path = "/addfavoriet/{id}")
    public Onderwerp favoriteOnderwerp(@PathVariable int id, Principal principal) {
        Onderwerp o = onderwerpservice.getOnderwerpById(id);

        ArrayList<Onderwerp> a = new ArrayList<>();
        for(Integer i : gebruikerservice.findStudentByUsername(principal.getName()).getFavorites()){
            a.add(onderwerpservice.getOnderwerpById(i));
        }
        a.add(o);
        gebruikerservice.findStudentByUsername(principal.getName()).setFavorites(a);
        gebruikerservice.favoriteOnderwerp(gebruikerservice.findStudentByUsername(principal.getName()));
        return o;
    }

    @DeleteMapping(path = "/deletefavoriet/{id}")
    public Onderwerp deleteFavoriteOnderwerp(@PathVariable Integer id, Principal principal) {
        onderwerpservice.deleteFavoriteOnderwerp(gebruikerservice.findStudentByUsername(principal.getName()),id);
        return onderwerpservice.getOnderwerpById(id);
    }

    @PostMapping(path = "/addselection/{i1}/{i2}/{i3}")
    public void addSelection(@PathVariable int i1,@PathVariable int i2,@PathVariable int i3, Principal principal){
        Map<Integer,Onderwerp> m = new HashMap<>();
        m.put(1,onderwerpservice.getOnderwerpById(i1));
        m.put(2,onderwerpservice.getOnderwerpById(i2));
        m.put(3,onderwerpservice.getOnderwerpById(i3));

        gebruikerservice.selectOnderwerpen(gebruikerservice.findStudentByUsername(principal.getName()), m);
    }

    @GetMapping(path = "/selection")
    public List<Onderwerp> getSelection(Principal principal){
        List<Onderwerp> l = new ArrayList<>();
        if(!gebruikerservice.findStudentByUsername(principal.getName()).getSelection().isEmpty()){
            l.add(onderwerpservice.getOnderwerpById(gebruikerservice.findStudentByUsername(principal.getName()).getSelection().get(1)));
            l.add(onderwerpservice.getOnderwerpById(gebruikerservice.findStudentByUsername(principal.getName()).getSelection().get(2)));
            l.add(onderwerpservice.getOnderwerpById(gebruikerservice.findStudentByUsername(principal.getName()).getSelection().get(3)));
        }
        return l;
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refresh_token = "null";
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if (cookie.getName().equals("refresh_token")){
                refresh_token = cookie.getValue();
            }
        }
        //String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(!refresh_token.equals("null")){
            try{
                //String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Gebruiker user = gebruikerservice.findByUsername(username);

                String acces_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000)) //Staat momenteel op 10 minuten
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRollen().stream().map(Rol::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Collection<Rol> rollen = user.getRollen();
                String roles = rollen.toString();
                Map<String, String> tokens = new HashMap<>();
                tokens.put("roles", roles);
                tokens.put("acces_token", acces_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping("/logout")
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        //als de refreshtoken wordt opgeslagen in de databse moet deze ook hier verwijderd worden
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("refresh_token")){
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    @GetMapping("/promotoren")//gebruik dit om keuze tussen promotoren weer te geven
    public ResponseEntity<List<Gebruiker>> getPromotoren(){
        return ResponseEntity.ok().body(gebruikerservice.findAllPromotoren());
    }

    @PostMapping("/boost/{oid}/{sid}")
    public Onderwerp boostStudent(@PathVariable int oid, @PathVariable long sid){
        return onderwerpservice.boostStudent(oid, sid);
    }

    @PostMapping("/ontboost/{oid}/{sid}")
    public Onderwerp ontBoostStudent(@PathVariable int oid, @PathVariable long sid){
        return onderwerpservice.ontBoostStudent(oid,sid);
    }

    @PostMapping("/toewijzen/{oid}/{sid}")
    public Student wijsToe(@PathVariable int oid, @PathVariable long sid){
        return onderwerpservice.wijsToe(oid, sid);
    }

    @GetMapping("/gettoegewezen")
    public Onderwerp getToegewezen(Principal principal){
        return onderwerpservice.getOnderwerpById(gebruikerservice.findStudentByUsername(principal.getName()).getToegewezen());
    }

    @PostMapping("/changepassword")
    public Gebruiker changePassword(@RequestParam String op,@RequestParam String np, Principal principal){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Gebruiker g = gebruikerservice.findByUsername(principal.getName());
        String op1 = g.getPassword();
        if(bCryptPasswordEncoder.matches(op,op1)){
            log.info("wachtwoord matcht");
            g.setPassword(np);
            return gebruikerservice.saveNewGebruiker(g);
        }
        else{
            log.info("wachtwoord matcht niet");
            throw new RuntimeException();
        }
    }

    @GetMapping("/account")
    public Gebruiker getAccount(Principal principal){
        return gebruikerservice.findByUsername(principal.getName());
    }

    @GetMapping("/bedrijftoegewezen")
    public Collection<Onderwerp> bedrijfToegewezen(Principal principal){
        return gebruikerservice.bedrijfToegewezen(gebruikerservice.findCompanyByUsername(principal.getName()));
    }
}
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
