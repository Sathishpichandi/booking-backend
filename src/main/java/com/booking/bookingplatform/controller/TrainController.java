package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.TrainRepository;
import com.booking.bookingplatform.service.TrainBookingService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainRepository trainRepository;
    private final TrainBookingService trainBookingService;

    public TrainController(TrainRepository trainRepository,
                           TrainBookingService trainBookingService) {
        this.trainRepository = trainRepository;
        this.trainBookingService = trainBookingService;
    }

    @GetMapping("/search")
    public List<Train> searchTrains(@RequestParam String source,
                                    @RequestParam String destination) {
        return trainRepository
                .findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);
    }

    @PostMapping("/book")
    public TrainBooking bookTrain(@RequestParam Long trainId,
                                  @RequestParam TrainClassType classType,
                                  Authentication authentication) {

        return trainBookingService.createBooking(
                trainId,
                classType,
                authentication.getName()
        );
    }


    @GetMapping
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }
}
