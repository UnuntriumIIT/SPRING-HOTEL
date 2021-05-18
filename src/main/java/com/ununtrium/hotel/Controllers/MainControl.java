package com.ununtrium.hotel.Controllers;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import com.ununtrium.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
public class MainControl {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homeControl(Model m){
        m.addAttribute("title", "SPRING HOTEL");
        return "index";
    }

    @GetMapping("/login")
    public String loginControl(Model m){
        return "login";
    }

    @GetMapping("/deny")
    public String accessDeny(Model m){
        return "deny";
    }

    @GetMapping("/admin")
    public String userList(Model model) {
        return "admin";
    }

}
