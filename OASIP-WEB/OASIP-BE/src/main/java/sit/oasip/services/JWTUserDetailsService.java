package sit.oasip.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class JWTUserDetailsService implements UserDetailsService {
@Autowired
private UserRepository repository;

@Autowired
private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        User userByEmail = repository.findByEmail(value);
        
        if (userByEmail != null) {
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userByEmail.getEmail(), userByEmail.getPassword(), new ArrayList<>());
            return user;
        } else {
            request.setAttribute("message","User not found with email: '" + value+"' It have been deleted, please login or signup again.");
            throw new UsernameNotFoundException("User not found with email: " + value);
        }


    }
}