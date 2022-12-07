package sit.oasip.config;

<<<<<<< HEAD
import com.azure.spring.cloud.autoconfigure.aad.AadResourceServerWebSecurityConfigurerAdapter;
=======
>>>>>>> front-end-develop
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import sit.oasip.utils.Role;

<<<<<<< HEAD
import javax.servlet.http.HttpServletRequest;
import java.util.*;


//@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends AadResourceServerWebSecurityConfigurerAdapter {
=======
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
>>>>>>> front-end-develop

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
<<<<<<< HEAD
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "IsRefreshToken"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        super.configure(httpSecurity);

        httpSecurity.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
//                .authorizeRequests()
//                .antMatchers("/api/login", "/api/users/signup").permitAll()
//                .anyRequest().authenticated()
//                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
=======

         CorsConfiguration corsConfiguration = new CorsConfiguration();
         corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type" , "IsRefreshToken"));
         corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
         corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
         corsConfiguration.setAllowCredentials(true);
         corsConfiguration.setExposedHeaders(List.of("Authorization"));


        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
                .authorizeRequests()
//                .antMatchers ("/api/events").permitAll()
                .antMatchers("/api/login", "/api/users/signup").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/events").anonymous()

                .antMatchers("/api/users", "/api/users/**", "/api/match").hasAuthority(Role.Admin.name())
                .antMatchers(HttpMethod.GET, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name(), Role.Lecturer.name())
                .antMatchers(HttpMethod.PUT, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name())
                .antMatchers(HttpMethod.DELETE, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name())
                .antMatchers(HttpMethod.POST, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name(),"Guest")

                .antMatchers(HttpMethod.GET,"/api/eventcategorys/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name(), Role.Lecturer.name(),"Guest")
                .antMatchers(HttpMethod.PUT,"/api/eventcategorys/**").hasAnyAuthority(Role.Admin.name())

                .antMatchers(HttpMethod.DELETE,"/api/file/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name())
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
>>>>>>> front-end-develop
    }


}