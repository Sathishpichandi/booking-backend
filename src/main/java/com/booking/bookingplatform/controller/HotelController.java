package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Hotel;
import com.booking.bookingplatform.entity.Room;
import com.booking.bookingplatform.repository.HotelRepository;
import com.booking.bookingplatform.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin("*")
public class HotelController {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public HotelController(HotelRepository hotelRepository,
                           RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    // ✅ GET ALL HOTELS
    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    // ✅ GET HOTEL BY ID
    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    // ✅ SEARCH HOTEL
    @GetMapping("/search")
    public List<Hotel> searchHotels(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double minRating) {

        return hotelRepository.searchHotels(city, minRating);
    }

    // ✅ GET ROOMS OF HOTEL
    @GetMapping("/{hotelId}/rooms")
    public List<Room> getRoomsByHotel(@PathVariable Long hotelId) {

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        return roomRepository.findByHotel(hotel);
    }
}