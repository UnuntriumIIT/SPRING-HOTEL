package com.ununtrium.hotel.service;

import com.ununtrium.hotel.Entity.Role;
import com.ununtrium.hotel.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import java.util.ArrayList;

@Component
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        long roles = roleRepository.count();
        if (roles == 0) {
            ArrayList<Role> rr = new ArrayList<>();
            rr.add(new Role(1L, "ROLE_ADMIN"));
            rr.add(new Role(2L, "ROLE_USER"));
            roleRepository.saveAll(rr);
        }
    }
}
