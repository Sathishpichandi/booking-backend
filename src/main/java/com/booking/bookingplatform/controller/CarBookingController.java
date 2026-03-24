package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.CarBookingRepository;
import com.booking.bookingplatform.repository.CarRepository;
import com.booking.bookingplatform.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/car-bookings")
public class CarBookingController {

    private final CarBookingRepository carBookingRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarBookingController(CarBookingRepository carBookingRepository,
                                CarRepository carRepository,
                                UserRepository userRepository) {
        this.carBookingRepository = carBookingRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    // CREATE BOOKING
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestParam Long carId,
                                           Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (car.getAvailableCars() <= 0) {
            return ResponseEntity.badRequest().body("No cars available");
        }

        car.setAvailableCars(car.getAvailableCars() - 1);
        carRepository.save(car);

        CarBooking booking = new CarBooking();
        booking.setUser(user);
        booking.setCar(car);
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        booking.setBookingCode(generateCode());

        return ResponseEntity.ok(carBookingRepository.save(booking));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {

        CarBooking booking = carBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car booking not found"));

        return ResponseEntity.ok(booking);
    }

    // CANCEL
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id,
                                           Authentication authentication) {

        CarBooking booking = carBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        Car car = booking.getCar();
        car.setAvailableCars(car.getAvailableCars() + 1);
        carRepository.save(car);

        carBookingRepository.save(booking);

        return ResponseEntity.ok("Car booking cancelled");
    }
    @GetMapping("/my")
    public List<CarBooking> myBookings(Authentication auth) {

        String email = auth.getName();

        return carBookingRepository.findByUser_Email(email);
    }



    private String generateCode() {
        return "CAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
