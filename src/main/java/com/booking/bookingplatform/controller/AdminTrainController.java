package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Train;
import com.booking.bookingplatform.repository.TrainRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/trains")
public class AdminTrainController {

    private final TrainRepository trainRepository;

    public AdminTrainController(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    // ================= GET ALL =================
    @GetMapping
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    // ================= CREATE =================
    @PostMapping
    public Train createTrain(@RequestBody Train train) {
        return trainRepository.save(train);
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public Train updateTrain(@PathVariable Long id,
                             @RequestBody Train updated) {

        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Train not found"));

        train.setTrainNumber(updated.getTrainNumber());
        train.setTrainName(updated.getTrainName());
        train.setSource(updated.getSource());
        train.setDestination(updated.getDestination());

        train.setSlPrice(updated.getSlPrice());
        train.setThreeAPrice(updated.getThreeAPrice());
        train.setTwoAPrice(updated.getTwoAPrice());

        train.setSlAvailableSeats(updated.getSlAvailableSeats());
        train.setThreeAAvailableSeats(updated.getThreeAAvailableSeats());
        train.setTwoAAvailableSeats(updated.getTwoAAvailableSeats());

        return trainRepository.save(train);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable Long id) {
        trainRepository.deleteById(id);
        return ResponseEntity.ok("Train deleted successfully");
    }
}