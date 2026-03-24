package com.booking.bookingplatform.service;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class HotelBookingService {

    private final RoomRepository roomRepository;
    private final HotelBookingRepository hotelBookingRepository;
    private final UserRepository userRepository;

    public HotelBookingService(RoomRepository roomRepository,
                               HotelBookingRepository hotelBookingRepository,
                               UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.hotelBookingRepository = hotelBookingRepository;
        this.userRepository = userRepository;
    }

    public HotelBooking createBooking(Long roomId,
                                      LocalDate checkIn,
                                      LocalDate checkOut,
                                      String userEmail) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (room.getAvailableRooms() <= 0) {
            throw new RuntimeException("No rooms available");
        }

        long days = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (days <= 0) {
            throw new RuntimeException("Invalid date selection");
        }

        double totalAmount = days * room.getPricePerNight();

        // Reduce availability
        room.setAvailableRooms(room.getAvailableRooms() - 1);
        roomRepository.save(room);

        HotelBooking booking = new HotelBooking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PAYMENT_PENDING); // ✅ change here
        booking.setBookingCode("HTL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return hotelBookingRepository.save(booking);
    }
}