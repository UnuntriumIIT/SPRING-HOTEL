package com.ununtrium.hotel.Repository;

import com.ununtrium.hotel.Entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {}