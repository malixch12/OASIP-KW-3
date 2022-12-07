package sit.oasip.config;

import com.azure.spring.cloud.autoconfigure.aad.AadJwtBearerTokenAuthenticationConverter;
import com.azure.spring.cloud.autoconfigure.aad.AadResourceServerWebSecurityConfigurerAdapter;
import com.azure.spring.cloud.autoconfigure.aad.properties.AadResourceServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.oauth2.server.resource.introspection.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.utils.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

//@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends AadResourceServerWebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }
//
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//
//
//    @Autowired
//    private AadResourceServerProperties properties;

//    Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();
//    JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);

//        @Bean
//    void tokenAuthenticationManagerResolver
//            (JwtDecoder jwtDecoder, OpaqueTokenIntrospector opaqueTokenIntrospector) {
//                AuthenticationManager jwt = new ProviderManager(new JwtAuthenticationProvider(jwtDecoder));
//                AuthenticationManager opaqueToken = new ProviderManager(
//                        new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector));
////                return (request) -> useJwt(request) ? jwt : opaqueToken;
//    }

//    Boolean useJwt(HttpServletRequest request) {
//        String token = request.getHeader("Authorization").replaceFirst("Bearer ", "");
//        if (jwtTokenUtil.validateToken(token)) {
//            return true;
//        }
//        return false;
//    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "IsRefreshToken"));
//        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
//        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setExposedHeaders(List.of("Authorization"));
//        System.out.println("2");
//
//        super.configure(http);
//
//
//        http.cors();
//        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
//
//        JwtIssuerAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerAuthenticationManagerResolver
//                ("https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d/v2.0", "http://localhost:8080/api/login");
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/api/login", "/api/users/signup").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2ResourceServer().authenticationManagerResolver(authenticationManagerResolver);
////
////        List<String> issuers = new ArrayList<>();
////        issuers.add("https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d/v2.0");
////        issuers.add("https://intproj21.sit.kmutt.ac.th/kw3/#/Login");
////        issuers.stream().forEach(issuer -> addManager(authenticationManagers, issuer));
////
////        http.httpBasic().disable()
////                .authorizeRequests(auth -> auth
////                        .antMatchers("/api/login", "/api/users/signup").permitAll()
////                        .antMatchers(HttpMethod.GET, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name(), Role.Lecturer.name())
////                        .anyRequest().authenticated()
////                );
//
//
////        http.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
////                .authorizeRequests()
////                .antMatchers("/api/login", "/api/users/signup").permitAll()
////                .antMatchers(HttpMethod.GET, "/api/events/**").hasAnyAuthority(Role.Student.name(), Role.Admin.name(), Role.Lecturer.name())
////                .anyRequest().authenticated().and().oauth2ResourceServer(oauth2ResourceServer -> {
////                    oauth2ResourceServer.authenticationManagerResolver(authenticationManagerResolver);
////                })
////                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }

//    public void addManager(Map<String, AuthenticationManager> authenticationManagers, String issuer) {
//        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromOidcIssuerLocation(issuer));
//        authenticationProvider.setJwtAuthenticationConverter(getJwtAuthenticationConverter());
//        authenticationManagers.put(issuer, authenticationProvider::authenticate);
//    }
//
//    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
//        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
//
//        conv.setJwtGrantedAuthoritiesConverter(jwt -> {
//
//            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
//            if (realmAccess == null) {
//                return new ArrayList<>();
//            }
//
//            List<String> roles = (List<String>) realmAccess.get("roles");
//            return roles.stream()
//                    .map(r -> "ROLE_" + r)
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//        });
//        return conv;
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "IsRefreshToken"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        super.configure(httpSecurity);
        httpSecurity.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
                .authorizeRequests()
                .antMatchers("/api/login", "/api/users/signup").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}