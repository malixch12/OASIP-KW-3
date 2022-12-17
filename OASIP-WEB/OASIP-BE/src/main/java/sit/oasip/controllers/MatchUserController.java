package sit.oasip.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.oasip.dtos.UserDTOs.MatchUserDTO;
import sit.oasip.services.UserService;

@RestController
@RequestMapping("/api/match")
public class MatchUserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('Admin')")
    @PostMapping("")
    public void matchUser(@RequestBody MatchUserDTO matchUser){
        userService.match(matchUser);

    }
}
