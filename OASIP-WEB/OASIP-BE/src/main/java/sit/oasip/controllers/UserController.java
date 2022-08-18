package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<UserDTO> getUserByAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize
    ){
        return userService.getUserAll(PageRequest.of(page, pageSize));
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User addUser(@Validated @RequestBody AddUserDTO newUser){
        return userService.add(newUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer userId){userService.delete(userId);}



}
