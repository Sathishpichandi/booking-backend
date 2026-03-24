package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Room;
import com.booking.bookingplatform.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotel(Hotel hotel);

}
