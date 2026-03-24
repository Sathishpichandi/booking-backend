package com.booking.bookingplatform.service;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.BusBookingRepository;
import com.booking.bookingplatform.repository.BusRepository;
import com.booking.bookingplatform.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusBookingService {

    private final BusRepository busRepository;
    private final BusBookingRepository busBookingRepository;
    private final UserRepository userRepository;

    public BusBookingService(BusRepository busRepository,
                             BusBookingRepository busBookingRepository,
                             UserRepository userRepository) {

        this.busRepository = busRepository;
        this.busBookingRepository = busBookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BusBooking createBooking(Long busId,
                                    String seatNumber,
                                    String email) {

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        if (bus.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }

        boolean seatExists =
                busBookingRepository.existsByBusAndSeatNumber(bus, seatNumber);

        if (seatExists) {
            throw new RuntimeException("Seat already booked");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        bus.setAvailableSeats(bus.getAvailableSeats() - 1);

        BusBooking booking = new BusBooking();

        booking.setBus(bus);
        booking.setUser(user);
        booking.setSeatNumber(seatNumber);
        booking.setAmount(bus.getPrice());
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        booking.setBookingCode(
                "BUS-" + UUID.randomUUID().toString().substring(0,8).toUpperCase()
        );

        return busBookingRepository.save(booking);
    }
}