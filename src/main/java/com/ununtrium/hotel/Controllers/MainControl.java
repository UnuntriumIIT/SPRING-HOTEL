package com.ununtrium.hotel.Controllers;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControl {

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
}
