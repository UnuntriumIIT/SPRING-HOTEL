package com.ununtrium.hotel.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ununtrium.hotel.HotelApplication.logger;

@Controller
public class MainControl {

    @GetMapping("/")
    public String homeControl(Model m){
        m.addAttribute("title", "SPRING HOTEL");
        logger.info("GET Request URL: \"/\"");
        return "index";
    }

    @GetMapping("/login")
    public String loginControl(Model m){
        logger.info("GET Request URL: \"/login\"");
        return "login";
    }

    @GetMapping("/deny")
    public String accessDeny(Model m){
        logger.info("GET Request URL: \"/deny\"");
        return "deny";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        logger.info("GET Request URL: \"/admin\"");
        return "admin";
    }

}
