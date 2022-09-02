package sit.oasip.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sit.oasip.Component.JwtTokenUtil;
import sit.oasip.dtos.UserDTO.MatchUserDTO;
import sit.oasip.javainuse.models.JwtResponse;
import sit.oasip.javainuse.services.JWTUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping("/api/login")
public class AuthenticateController{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MatchUserDTO matchUserDTO) throws Exception {

        authenticate(matchUserDTO.getEmail(), matchUserDTO.getPassword());


//        final User user =  repository.findByEmail(matchUserDTO.getEmail());
//        final String token = jwtTokenUtil.generateToken(user);
//       authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
       final UserDetails userDetails = userDetailsService
               .loadUserByUsername(matchUserDTO.getEmail());
       final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
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
}