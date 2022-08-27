package sit.oasip;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sit.oasip.utils.ListMapper;
import sit.oasip.utils.PageMapper;

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
    public BCryptPasswordEncoder bCryptPasswordEncoder(){return new BCryptPasswordEncoder();}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return null;
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return false;
            }
        };
    }



}
