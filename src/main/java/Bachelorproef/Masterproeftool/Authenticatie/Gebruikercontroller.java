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
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final Gebruikerservice gebruikerservice;
    private final Rolservice rolservice;
    private final Onderwerpservice onderwerpservice;

    @GetMapping("/gebruikers")
    public ResponseEntity<List<Gebruiker>> getGebruikers(){
        return ResponseEntity.ok().body(gebruikerservice.findAllGebruikers());
    }

    @PostMapping("/gebruikers/save")
    public ResponseEntity<Gebruiker> saveGebruiker(@RequestBody Gebruiker gebruiker){
        return ResponseEntity.ok().body(gebruikerservice.saveGebruiker(gebruiker));
    }

    @PostMapping("/gebruikers/savestudent")
    public ResponseEntity<Gebruiker> saveGebruiker(@RequestBody Student gebruiker){
        return ResponseEntity.ok().body(gebruikerservice.saveGebruiker(gebruiker));
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
        Gebruiker g = gebruikerservice.findByUsername(principal.getName());
        return ResponseEntity.ok().body(g.getFavorites());
    }

    @PostMapping(path = "/addfavoriet/{id}")
    public Onderwerp favoriteOnderwerp(@PathVariable int id, Principal principal) {
        Onderwerp o = onderwerpservice.getOnderwerpById(id);
        gebruikerservice.favoriteOnderwerp(gebruikerservice.findByUsername(principal.getName()),o);
        return o;
    }

    @DeleteMapping(path = "/deletefavoriet/{id}")
    public Onderwerp deleteFavoriteOnderwerp(@PathVariable int id, Principal principal) {
        Onderwerp o = onderwerpservice.getOnderwerpById(id);
        gebruikerservice.deleteFavoriteOnderwerp(gebruikerservice.findByUsername(principal.getName()),o);
        return o;
    }

    @PostMapping(path = "/addselection/{i1}/{i2}/{i3}")
    public void addSelection(@PathVariable int i1,@PathVariable int i2,@PathVariable int i3, Principal principal){
        ArrayList<Integer> ids = new ArrayList();
        ids.add(i1);
        ids.add(i2);
        ids.add(i3);
        gebruikerservice.selectOnderwerpen(gebruikerservice.findByUsername(principal.getName()), onderwerpservice.getOnderwerpenById(ids));
    }

    @GetMapping(path = "/selection")
    public Collection<Onderwerp> getSelection(Principal principal){
        return gebruikerservice.getSelection(gebruikerservice.findByUsername(principal.getName()));
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
    public void logOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

}
@Data
class RoleToUserForm{
    private String username;
    private String rolename;
}
