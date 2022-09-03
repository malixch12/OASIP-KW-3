package sit.oasip;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sit.oasip.javainuse.config.JwtAuthenticationEntryPoint;

import sit.oasip.javainuse.services.JWTUserDetailsService;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

import javax.annotation.PostConstruct;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
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

}
