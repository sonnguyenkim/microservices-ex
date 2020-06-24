package com.sn.ex.zuulproxy.controller;

import com.sn.ex.zuulproxy.entities.AppUser;
import com.sn.ex.zuulproxy.model.AuthenticationRequest;
import com.sn.ex.zuulproxy.model.AuthenticationResponse;
import com.sn.ex.zuulproxy.service.AppUserDetailsService;
import com.sn.ex.zuulproxy.service.AppUserService;
import com.sn.ex.zuulproxy.service.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtTokenUtil;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping({ "/hello" })
    public String firstPage() {
        return "Hello World";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> signup(@RequestBody AppUser appUser) throws Exception {
        return ResponseEntity.ok().body(appUserService.save(appUser));
    }
}
