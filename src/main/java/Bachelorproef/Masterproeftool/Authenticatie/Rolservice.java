package Bachelorproef.Masterproeftool.Authenticatie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class Rolservice implements UserDetailsService {
    private final Rolrepository rolrepository;  //in orde door @RequiredArgsConstructor tag, kan ook met @Autowire
    private final Gebruikerservice gebruikerservice;

    public Gebruiker saveGebruiker(Gebruiker g){
        log.info("saving new user to database");
        return gebruikerservice.saveNewGebruiker(g);
    }

    public Rol saveRol(Rol r){
        log.info("saving new role to database");
        return rolrepository.save(r);
    }

    public void addRolToGebruiker(String username, String rolnaam ){
        log.info("adding new role to user");
        Gebruiker gebruiker = gebruikerservice.findByUsername(username);    //Hier nog meer logica en checks nodig!!
        Rol rol = rolrepository.findByName(rolnaam);
        gebruiker.getRollen().add(rol);
    }

    public Gebruiker getGebruiker(String username){
        return gebruikerservice.findByUsername(username);
    }

    public List<Gebruiker> getGebruikers(){
        return gebruikerservice.findAllGebruikers();
    }

    //Userdetailservice
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Gebruiker gebruiker = gebruikerservice.findByUsername(username);
        if(gebruiker == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else{
            log.info("User found");

        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        gebruiker.getRollen().forEach(rol -> {authorities.add(new SimpleGrantedAuthority(rol.getName()));});
        return new org.springframework.security.core.userdetails.User(gebruiker.getUsername(), gebruiker.getPassword(), authorities); //andere user dan "gebruiker"
    }
}
