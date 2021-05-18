package com.ununtrium.hotel.service;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public Room findRoomById(Long Id) {
        Optional<Room> roomFromDb = roomRepository.findById(Id);
        return roomFromDb.orElse(new Room());
    }

    public Iterable<Room> allRooms() {
        return roomRepository.findAll();
    }

    public boolean saveRoom(Room room) {
        Iterable<Room> a = this.allRooms();
        boolean flag = true;
        for (Room r: a) {
            if (r.getId() == room.getId()
                    || r.getRoom().equals(room.getRoom())
                    || room.getRoom().equals("")
                    || room.getRoom() == null
                    || room.getRoom() == ""){
                flag = false;
                break;
            }
        }

        if (flag) {
            roomRepository.save(room);
        }
        return flag;
    }

    public boolean saveRoom(Room newRoom, Long roomId) {
        Iterable<Room> a = this.allRooms();
        Room oldRoom = roomRepository.findById(roomId).get();
        boolean flag = true;
        for (Room r: a) {
            if (r.getId() != roomId) {
                if (newRoom.getRoom() == r.getRoom()){
                    flag = false;
                    break;
                }
            }
        }

        if (flag) {
            roomRepository.deleteById(roomId);
            roomRepository.save(newRoom);
        }
        return flag;
    }

    public boolean deleteRoom(Long Id) {
        if (roomRepository.findById(Id).isPresent()) {
            roomRepository.deleteById(Id);
            return true;
        }
        return false;
    }
}
