package com.okta.developer.oidc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MainController {

    @GetMapping("/")
    String home(Principal user){
        return "Hello " + user.getName();
    }
}
