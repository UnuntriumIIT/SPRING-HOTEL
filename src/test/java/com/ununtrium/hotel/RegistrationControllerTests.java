package com.ununtrium.hotel;

import com.ununtrium.hotel.Entity.Role;
import com.ununtrium.hotel.Entity.User;
import com.ununtrium.hotel.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void testContextLoad() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Регистрация")));
    }

    @Test
    void testRegistration() throws Exception {
        try {
            userService.deleteUser((User) userService.loadUserByUsername("this is tester"));
        }
        catch (UsernameNotFoundException e){
            System.out.println(e.getMessage());
        }
        User u = new User();
        u.setUsername("this is tester");
        u.setPassword("kek");
        u.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        this.mockMvc.perform(post("/registration").flashAttr("userForm", u))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        userService.deleteUser((User)userService.loadUserByUsername("this is tester"));
    }
}