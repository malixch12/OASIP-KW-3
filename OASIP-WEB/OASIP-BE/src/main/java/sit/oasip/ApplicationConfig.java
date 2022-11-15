package sit.oasip;


import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import sit.oasip.Component.FileStorageProperties;
import sit.oasip.dtos.JwtResponse;
import sit.oasip.services.AuthenticationService;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

import java.nio.file.Path;


@Configuration
@EnableConfigurationProperties({
        FileStorageProperties.class
})
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
