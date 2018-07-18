package com.myapp.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cgh
 */
@Controller
public class UserController {
    @GetMapping("/user/login")
    public String loginPage(){
        return "user/login";
    }

    @GetMapping("/user/center")
    public String centerPage(){
        return "user/center";
    }
}
