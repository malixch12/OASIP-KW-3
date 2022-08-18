package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.oasip.dtos.UserDTO.AddUserDTO;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.entities.User;
import sit.oasip.repositories.UserRepository;
import sit.oasip.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @GetMapping("")
    public List<UserDTO> getUserByAll(){
        return userService.getUserAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User addUser(@Validated @RequestBody AddUserDTO newUser){
        return userService.add(newUser);
    }

}
