package com.ununtrium.hotel.service;

import com.ununtrium.hotel.Entity.Room;
import com.ununtrium.hotel.Repository.RoomRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

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
            Timestamp t = new Timestamp(System.currentTimeMillis());
            room.setDatetime(t.toString());
            roomRepository.save(room);
        }
        return flag;
    }

    public boolean saveRoom(Room newRoom, Long roomId) {
        Iterable<Room> a = this.allRooms();
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

    public Room findRoomByID(Long roomId) throws NotFoundException {
        Room r = roomRepository.findById(roomId).orElseThrow(() -> new NotFoundException("Room cannot be found!"));
        return r;
    }

    public Room findRoomByNumber(String number) throws NotFoundException {
        Iterable<Room> allRm = this.allRooms();
        Room r = new Room();
        for (Room room: allRm) {
            if (room.getRoom().equals(number)) {
                r = room;
                break;
            }
        }
        return r;
    }

    public boolean deleteRoom(Long Id) {
        if (roomRepository.findById(Id).isPresent()) {
            roomRepository.deleteById(Id);
            return true;
        }
        return false;
    }
}
