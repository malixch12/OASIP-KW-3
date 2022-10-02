package sit.oasip;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import sit.oasip.controllers.EventController;
import sit.oasip.javainuse.models.JwtResponse;
import sit.oasip.repositories.EventRepository;
import sit.oasip.repositories.EventcategoryRepository;
import sit.oasip.services.AuthenticationService;
import sit.oasip.services.EventService;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
            return new ModelMapper();
    }
    @Bean
    public ListMapper listMapper(){ return new ListMapper();}
    @Bean
    public PageMapper pageMapper(){ return new PageMapper();}
    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder(){
        return new Argon2PasswordEncoder(16,29,1,16,1);
    }
    @Bean
    public JwtResponse jwtResponse(){return new JwtResponse();}
    @Bean
    public AuthenticationService authenticationService(){return  new AuthenticationService();}

}
