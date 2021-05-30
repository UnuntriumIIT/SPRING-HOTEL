package com.ununtrium.hotel;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("123")
class RoomsControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    RoomService rs;

    @Test
    void testRoomList() throws Exception {
        this.mockMvc.perform(get("/booking"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/main/div").nodeCount(3));
    }

    @Test
    void testAddRoom() throws Exception {
        rs.deleteRoom(rs.findRoomByNumber("99999999999999999").getId());
        Room testRoom = new Room();
        testRoom.setUserName("testMethod");
        testRoom.setBusy(true);
        testRoom.setRoom("99999999999999999");
        testRoom.setDatetime(String.valueOf(System.currentTimeMillis()));
        testRoom.setDescr("testestestestestestestesetsetststsetestest");
        this.mockMvc.perform(post("/admin/add").flashAttr("roomForm", testRoom))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/admin/change"));
        rs.deleteRoom(rs.findRoomByNumber("99999999999999999").getId());
    }

    @Test
    void testDeleteRoom() throws Exception {
        rs.deleteRoom(rs.findRoomByNumber("99999999999999999").getId());
        Room testRoom = new Room();
        testRoom.setUserName("testMethod");
        testRoom.setBusy(true);
        testRoom.setRoom("99999999999999999");
        testRoom.setDatetime(String.valueOf(System.currentTimeMillis()));
        testRoom.setDescr("testestestestestestestesetsetststsetestest");
        rs.saveRoom(testRoom);
        Long id = rs.findRoomByNumber("99999999999999999").getId();
        this.mockMvc.perform(get("/delete_room").param("roomId", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/admin/change"));
    }

    @Test
    void testEditRoom() throws Exception {
        rs.deleteRoom(rs.findRoomByNumber("99999999999999999").getId());
        Room testRoom = new Room();
        testRoom.setUserName("testMethod");
        testRoom.setBusy(true);
        testRoom.setRoom("99999999999999999");
        testRoom.setDatetime(String.valueOf(System.currentTimeMillis()));
        testRoom.setDescr("testestestestestestestesetsetststsetestest");
        rs.saveRoom(testRoom);
        testRoom.setUserName("i am not tester");
        Long id = rs.findRoomByNumber("99999999999999999").getId();
        this.mockMvc.perform(post("/edit_room").param("roomId", String.valueOf(id)).flashAttr("roomForm", testRoom))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/admin/change"));
        rs.deleteRoom(rs.findRoomByNumber("99999999999999999").getId());
    }
}