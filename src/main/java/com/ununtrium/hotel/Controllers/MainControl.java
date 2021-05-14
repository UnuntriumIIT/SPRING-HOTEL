package com.ununtrium.hotel.Controllers;

import com.ununtrium.hotel.Entity.Role;
import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoleRepository;
import com.ununtrium.hotel.Repository.RoomRepository;
import com.ununtrium.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/booking")
    public String bookControl(Model m){
        Iterable<Room> rooms = roomRepository.findAll();
        m.addAttribute("rooms", rooms);
        return "book-main";
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
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }
}
