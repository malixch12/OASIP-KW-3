package sit.oasip.javainuse.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService {
@Autowired
private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        User userByEmail = repository.findByEmail(value);

        if (userByEmail != null) {
            return new org.springframework.security.core.userdetails.User(userByEmail.getName(), userByEmail.getPassword(), new ArrayList<>());
        }

        User userByName = repository.findByName(value);

        if(userByName!=null){
            return new org.springframework.security.core.userdetails.User(userByName.getName(), userByName.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + value);
        }

    }
}