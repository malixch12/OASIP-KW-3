package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.oasip.dtos.UserDTO.UserDTO;
import sit.oasip.repositories.UserRepository;
import sit.oasip.services.UserService;

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

}
