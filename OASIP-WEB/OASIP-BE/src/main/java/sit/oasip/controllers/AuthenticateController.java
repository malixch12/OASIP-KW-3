package sit.oasip.controllers;


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
import sit.oasip.javainuse.models.JwtResponse;
import sit.oasip.javainuse.services.JWTUserDetailsService;
import sit.oasip.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthenticateController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MatchUserDTO matchUserDTO) throws Exception {
        User user = repository.findByEmail(matchUserDTO.getEmail());
        if (user != null) {
            authenticate(matchUserDTO.getEmail(), matchUserDTO.getPassword());

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(matchUserDTO.getEmail());
            System.out.println(userDetails.getAuthorities());
            final String token = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok().body(new JwtResponse(token));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + matchUserDTO.getEmail());
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request){
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok().body(new JwtResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}