package Bachelorproef.Masterproeftool.Authenticatie;

import Bachelorproef.Masterproeftool.Authenticatie.Filter.CustomAuthenticationFilter;
import Bachelorproef.Masterproeftool.Authenticatie.Filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.hibernate.criterion.Restrictions.and;
import static org.springframework.http.HttpMethod.*;


@Configuration @EnableWebSecurity
public class Securityconfig extends WebSecurityConfigurerAdapter {

    public Securityconfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new Gebruikerdetailsservice();
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and();
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");                 //hier pad bepalen om login naar te sturen
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers( "/login" , "/auth/refreshtoken","/auth/logout","/auth/promotoren","/auth/bedrijven","/auth/account").permitAll()  //geen authenticatie op dit pad DOE DIT MET LOGINPAD
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/addonderwerp/*").hasAnyAuthority("Student","Admin","Coordinator","Promotor","Bedrijf")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/auth/favorieten").hasAnyAuthority("Student")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/addfavoriet/*").hasAnyAuthority("Student")
                .and()
                .authorizeRequests()
                .antMatchers(DELETE, "/auth/deletefavoriet/*").hasAnyAuthority("Student")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/voegToe/*").hasAnyAuthority("Admin","Coordinator")
                .and()
                .authorizeRequests()
                .antMatchers(DELETE, "/verwijder/*").hasAnyAuthority("Admin","Coordinator")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/addselection/**").hasAnyAuthority("Admin","Student")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/auth/selection").hasAnyAuthority("Admin","Student")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/selection/*").hasAnyAuthority("Admin","Coordinator","Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/bedrijfaddonderwerp/*").hasAnyAuthority("Admin","Bedrijf","Coordinator")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/addonderwerpmetbedrijf/**").hasAnyAuthority("Admin","Bedrijf","Coordinator","Promotor","Student")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/promotoronderwerpen").hasAnyAuthority("Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/boost/**").hasAnyAuthority("Admin","Coordinator","Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/ontboost/**").hasAnyAuthority("Admin","Coordinator","Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/toewijzen/**").hasAnyAuthority("Admin","Coordinator","Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/onttoewijzen/**").hasAnyAuthority("Admin","Coordinator","Promotor")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/auth/gettoegewezen").hasAnyAuthority("Student")
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/auth/changepassword").hasAnyAuthority("Admin","Coordinator","Promotor","Student")
                .and()
                .authorizeRequests()
                .antMatchers(GET, "/onderwerpen").hasAnyAuthority("Student","Admin","Coordinator","Promotor","Bedrijf");



//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated();//doe permitAll() om niet te moeten inloggen
//        http.authorizeRequests()
//                .anyRequest().permitAll();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure(); //https op heroku
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
