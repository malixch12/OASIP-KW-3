package sit.oasip.controllers;

import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.dtos.UserDTO.MatchUserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchUserController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;

    @Autowired
    private UserRepository repository;

    @PostMapping("")
    public void matchUser(@RequestBody MatchUserDTO matchUser){
        User user = repository.findByEmail(matchUser.getEmail());

        String message = "";
        if(user != null){

            boolean isMatchPassword = argon2PasswordEncoder.matches(matchUser.getPassword(),user.getPassword());

            if(isMatchPassword){

                throw new ResponseStatusException(HttpStatus.OK,"Password Match");
            }
            else if(!isMatchPassword){

                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Password NOT Match");
            }

        }else if(user == null) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A user with the specified email DOES NOT exist");
        }

    }
}
