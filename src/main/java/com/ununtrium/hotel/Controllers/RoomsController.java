package com.ununtrium.hotel.Controllers;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import com.ununtrium.hotel.service.RoomService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ununtrium.hotel.HotelApplication.logger;

@Controller
public class RoomsController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/booking")
    public String bookControl(Model m){
        logger.info("GET Request URL: \"/booking\"");
        Iterable<Room> rooms = roomService.allRooms();
        m.addAttribute("rooms", rooms);
        return "book-main";
    }

    @GetMapping("/admin/change")
    public String listRooms(Model model) {
        logger.info("GET Request URL: \"/admin/change\"");
        Iterable<Room> rooms = roomService.allRooms();
        model.addAttribute("allRooms", rooms);
        model.addAttribute("roomForm", new Room());
        return "change";
    }

    @GetMapping("/admin/add")
    public String addRoom(Model model) {
        logger.info("GET Request URL: \"/admin/add\"");
        model.addAttribute("roomForm", new Room());
        return "add";
    }

    @PostMapping("/admin/add")
    public String submitRoom(@ModelAttribute("roomForm") @Valid Room roomForm, BindingResult bindingResult, Model model) {
        logger.info("POST Request URL: \"/admin/add\"");
        if (!roomService.saveRoom(roomForm)){
            return "add";
        }
        return "redirect:/admin/change";
    }

    @RequestMapping(value = "/delete_room", method = RequestMethod.GET)
    public String handleDeleteRoom(@RequestParam(name="roomId") String roomId) {
        logger.info("GET Request URL: \"/delete_room\"");
        Long id = Long.parseLong(roomId);
        roomService.deleteRoom(id);
        return "redirect:/admin/change";
    }

    @RequestMapping(value = "/edit_room", method = RequestMethod.GET)
    public String handleEditRoom(@RequestParam(name="roomId") String roomId, Model model) throws NotFoundException {
        logger.info("GET Request URL: \"/edit_room\"");
        Long id = Long.parseLong(roomId);
        Room room = roomService.findRoomByID(id);
        String r = room.getRoom();
        String d = room.getDescr();
        String u = room.getUserName();
        float c = room.getCost();
        boolean b = room.isBusy();
        model.addAttribute("rmid", id);
        model.addAttribute("room", r);
        model.addAttribute("descr", d);
        model.addAttribute("user", u);
        model.addAttribute("busy", b);
        model.addAttribute("cost", c);
        model.addAttribute("roomForm", new Room());
        return "room";
    }

    @RequestMapping(value = "/edit_room", method = RequestMethod.POST)
    public String submitEditRoom(@RequestParam(name="roomId") String roomId, @ModelAttribute("roomForm") @Valid Room roomForm, Model model) {
        logger.info("POST Request URL: \"/edit_room\"");
        Long id = Long.parseLong(roomId);
        if (!roomService.saveRoom(roomForm, id)){
            return "redirect:/";
        }
        else {
            return "redirect:/admin/change";
        }
    }

    @RequestMapping(value = "/accept_book", method = RequestMethod.GET)
    public String acceptBook(@RequestParam(name="roomId") String roomId, Model model) throws NotFoundException {
        logger.info("GET Request URL: \"/accept_book\"");
        Long id = Long.parseLong(roomId);
        Room r = roomService.findRoomByID(id);
        r.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        r.setBusy(true);
        roomService.saveRoom(r, id);
        return "redirect:/booking";
    }

    @RequestMapping(value = "/delete_book", method = RequestMethod.GET)
    public String deleteBook(@RequestParam(name="roomId") String roomId, Model model) throws NotFoundException {
        logger.info("GET Request URL: \"/delete_book\"");
        Long id = Long.parseLong(roomId);
        Room r = roomService.findRoomByID(id);
        r.setBusy(false);
        roomService.saveRoom(r, id);
        return "redirect:/booking";
    }
}

