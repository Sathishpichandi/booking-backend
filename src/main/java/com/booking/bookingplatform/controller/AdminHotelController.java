package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Hotel;
import com.booking.bookingplatform.entity.Room;
import com.booking.bookingplatform.repository.HotelRepository;
import com.booking.bookingplatform.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/hotels")
public class AdminHotelController {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public AdminHotelController(HotelRepository hotelRepository,
                                RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    // GET ALL HOTELS
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // CREATE HOTEL
    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    // UPDATE HOTEL
    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable Long id,
                             @RequestBody Hotel updated) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        hotel.setName(updated.getName());
        hotel.setCity(updated.getCity());
        hotel.setAddress(updated.getAddress());
        hotel.setRating(updated.getRating());

        return hotelRepository.save(hotel);
    }

    // DELETE HOTEL
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelRepository.deleteById(id);
        return ResponseEntity.ok("Hotel deleted successfully");
    }

    // ADD ROOM
    @PostMapping("/{hotelId}/rooms")
    public Room addRoom(@PathVariable Long hotelId,
                        @RequestBody Room room) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    // UPDATE ROOM PRICE
    @PutMapping("/rooms/{roomId}/price")
    public Room updateRoomPrice(@PathVariable Long roomId,
                                @RequestParam double price) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        room.setPricePerNight(price);
        return roomRepository.save(room);
    }

    // DELETE ROOM
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        roomRepository.deleteById(roomId);
        return ResponseEntity.ok("Room deleted successfully");
    }
}
