package Bachelorproef.Masterproeftool.Authenticatie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

public class Gebruikerdetailsservice implements UserDetailsService {

    @Autowired
    private Gebruikerrepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Gebruiker user = userRepository.findByUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with that username");
        }

        return new Gebruikerdetails(user);
    }
}
