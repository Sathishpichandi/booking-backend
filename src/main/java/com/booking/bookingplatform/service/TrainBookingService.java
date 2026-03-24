package com.booking.bookingplatform.service;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.TrainBookingRepository;
import com.booking.bookingplatform.repository.TrainRepository;
import com.booking.bookingplatform.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrainBookingService {

    private final TrainRepository trainRepository;
    private final TrainBookingRepository trainBookingRepository;
    private final UserRepository userRepository;

    public TrainBookingService(TrainRepository trainRepository,
                               TrainBookingRepository trainBookingRepository,
                               UserRepository userRepository) {
        this.trainRepository = trainRepository;
        this.trainBookingRepository = trainBookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TrainBooking createBooking(Long trainId,
                                      TrainClassType classType,
                                      String email) {

        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double price;
        switch (classType) {
            case SL -> {
                if (train.getSlAvailableSeats() <= 0)
                    throw new RuntimeException("No SL seats available");
                train.setSlAvailableSeats(train.getSlAvailableSeats() - 1);
                price = train.getSlPrice();
            }
            case THREE_A -> {
                if (train.getThreeAAvailableSeats() <= 0)
                    throw new RuntimeException("No 3A seats available");
                train.setThreeAAvailableSeats(train.getThreeAAvailableSeats() - 1);
                price = train.getThreeAPrice();
            }
            case TWO_A -> {
                if (train.getTwoAAvailableSeats() <= 0)
                    throw new RuntimeException("No 2A seats available");
                train.setTwoAAvailableSeats(train.getTwoAAvailableSeats() - 1);
                price = train.getTwoAPrice();
            }
            default -> throw new RuntimeException("Invalid class type");
        }

        TrainBooking booking = new TrainBooking();
        booking.setTrain(train);
        booking.setUser(user);
        booking.setClassType(classType);
        booking.setAmount(price);
        booking.setStatus(BookingStatus.PAYMENT_PENDING);
        booking.setBookingCode("TRN-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());

        return trainBookingRepository.save(booking);
    }
}
