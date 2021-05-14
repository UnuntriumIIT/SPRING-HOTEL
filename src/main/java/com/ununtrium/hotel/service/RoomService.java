package com.ununtrium.hotel.service;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public Room findRoomById(Long Id) {
        Optional<Room> roomFromDb = roomRepository.findById(Id);
        return roomFromDb.orElse(new Room());
    }

    public List<Room> allRooms() {
        return roomRepository.findAll();
    }

    public boolean saveRoom(Room room) {
        if (roomRepository.findById(room.getId()) != null) return false;
        roomRepository.save(room);
        return true;
    }

    public boolean deleteRoom(Long Id) {
        if (roomRepository.findById(Id).isPresent()) {
            roomRepository.deleteById(Id);
            return true;
        }
        return false;
    }
}
