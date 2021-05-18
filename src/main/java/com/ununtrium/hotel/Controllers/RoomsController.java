package com.ununtrium.hotel.Controllers;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import com.ununtrium.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class RoomsController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/booking")
    public String bookControl(Model m){
        Iterable<Room> rooms = roomRepository.findAll();
        m.addAttribute("rooms", rooms);
        return "book-main";
    }

    @GetMapping("/admin/change")
    public String listRooms(Model model) {
        Iterable<Room> rooms = roomRepository.findAll();
        Timestamp t = LocalDateTime.now();
        model.addAttribute("allRooms", rooms);
        model.addAttribute("roomForm", new Room());
        return "change";
    }

    @GetMapping("/admin/add")
    public String addRoom(Model model) {
        model.addAttribute("roomForm", new Room());
        return "add";
    }

    @PostMapping("/admin/add")
    public String submitRoom(@ModelAttribute("roomForm") @Valid Room roomForm, BindingResult bindingResult, Model model) {
        if (!roomService.saveRoom(roomForm)){
            return "add";
        }
        return "redirect:/admin/change";
    }

    @RequestMapping(value = "/delete_room", method = RequestMethod.GET)
    public String handleDeleteRoom(@RequestParam(name="roomId") String roomId) {
        Long id = Long.parseLong(roomId);
        roomRepository.deleteById(id);
        return "redirect:/admin/change";
    }

    @RequestMapping(value = "/edit_room", method = RequestMethod.GET)
    public String handleEditRoom(@RequestParam(name="roomId") String roomId, Model model) {
        Long id = Long.parseLong(roomId);
        Room room = roomRepository.findById(id).get();
        String r = room.getRoom();
        String d = room.getDescr();
        String u = room.getUserName();
        boolean b = room.isBusy();
        model.addAttribute("rmid", id);
        model.addAttribute("room", r);
        model.addAttribute("descr", d);
        model.addAttribute("user", u);
        model.addAttribute("busy", b);
        model.addAttribute("roomForm", new Room());
        return "room";
    }

    @RequestMapping(value = "/edit_room", method = RequestMethod.POST)
    public String submitEditRoom(@RequestParam(name="roomId") String roomId, @ModelAttribute("roomForm") @Valid Room roomForm, Model model) {
        Long id = Long.parseLong(roomId);
        if (!roomService.saveRoom(roomForm, id)){
            return "change";
        }
        else {
            return "redirect:/admin/change";
        }
    }
}

