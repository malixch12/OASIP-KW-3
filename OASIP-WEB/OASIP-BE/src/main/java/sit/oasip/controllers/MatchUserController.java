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
import sit.oasip.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchUserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public void matchUser(@RequestBody MatchUserDTO matchUser){
        userService.match(matchUser);
    }
}
