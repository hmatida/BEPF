package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.CurrentUser;
import br.com.personal_financee.pf.security.JwtAuthenticationRequest;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping(value = "/api/auth")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException{
        final Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  authenticationRequest.getLogin(),
                  authenticationRequest.getPassword()
          )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Users users = userRepository.findByLogin(authenticationRequest.getLogin());
        users.setPassword(null);
        return ResponseEntity.ok(new CurrentUser(token, users));
    }

    @PostMapping (value = "/api/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        final Users users = userRepository.findByLogin(userName);

        if (jwtTokenUtil.canTokenBeRefreshed(token)){
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new CurrentUser(refreshedToken, users));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
