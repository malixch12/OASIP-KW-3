package sit.oasip.controllers;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.oasip.Component.JwtTokenUtil;
//import sit.oasip.Component.JwtUtil;
import sit.oasip.dtos.UserDTOs.MatchUserDTO;
import sit.oasip.entities.User;
import sit.oasip.javainuse.config.JwtRequestFilter;
import sit.oasip.javainuse.models.JwtResponse;
import sit.oasip.javainuse.services.JWTUserDetailsService;
import sit.oasip.repositories.UserRepository;
import sit.oasip.services.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenticateController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MatchUserDTO matchUserDTO) throws Exception {
        User user = repository.findByEmail(matchUserDTO.getEmail());
        if (user != null) {
            authenticationService.authenticate(matchUserDTO.getEmail(), matchUserDTO.getPassword());
            userDetailsService.loadUserByUsername(matchUserDTO.getEmail());
            return ResponseEntity.ok().body(jwtTokenUtil.generateToken(user));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + matchUserDTO.getEmail());
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request){
        String token = jwtRequestFilter.extractJwtFromRequest(request);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
//        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        System.out.println(claims);

        Map<String, Object> expectedMap = authenticationService.getMapFromIoJsonwebtokenClaims(claims);

        return ResponseEntity.ok().body(jwtTokenUtil.doGenerateAccessToken(expectedMap.get("role").toString(),expectedMap.get("sub").toString()));
    }


}