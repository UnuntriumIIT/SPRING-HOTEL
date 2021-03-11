package com.ununtrium.hotel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

    @GetMapping("/")
    public String homeHandler(Model m){
        return "index";
    }

    @GetMapping("/login")
    public String loginHandler(Model m){
        return "login";
    }

}
