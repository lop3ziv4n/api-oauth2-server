package ar.org.blb.security.administration.controller;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@EnableResourceServer
@RequestMapping("/api/v1/oauth/")
public class AuthUserController {

    @GetMapping(value = "user")
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }
}
