package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sit.oasip.dtos.UserDTOs.AddUserDTO;
import sit.oasip.dtos.UserDTOs.EditUserDTO;
import sit.oasip.dtos.UserDTOs.GetUserDTO;
import sit.oasip.entities.User;
import sit.oasip.services.UserService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public Page<GetUserDTO> getUserByAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "8") Integer pageSize
    ){
        return userService.getUserAll(PageRequest.of(page, pageSize));
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public List<GetUserDTO> getLecturer(
            @PathVariable String role){
        return userService.getUserByRole(role);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/{userId}")
    public GetUserDTO getEventById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAnyAuthority('Lecturer')")
    @GetMapping("/byEmail")
    public GetUserDTO getEventById() {
        return userService.getUserByEmail();
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public User addUser(@Validated @RequestBody AddUserDTO newUser){
        return userService.add(newUser);
    }

    @PreAuthorize("hasAnyAuthority('Admin')")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Integer userId){userService.delete(userId);}

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User edit(@Validated @RequestBody EditUserDTO editUserDTO, @PathVariable Integer userId){
        return userService.edit(editUserDTO,userId);
    }
}
