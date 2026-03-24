package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Car;
import com.booking.bookingplatform.repository.CarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cars")
public class AdminCarController {

    private final CarRepository carRepository;

    public AdminCarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // ================= CREATE =================
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    // ================= GET ALL =================
    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id,
                                         @RequestBody Car updatedCar) {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setBrand(updatedCar.getBrand());
        car.setCity(updatedCar.getCity());
        car.setCarType(updatedCar.getCarType());
        car.setPricePerDay(updatedCar.getPricePerDay());
        car.setAvailableCars(updatedCar.getAvailableCars());

        Car saved = carRepository.save(car);

        return ResponseEntity.ok(saved);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id) {

        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        carRepository.delete(car);

        return ResponseEntity.ok("Car deleted successfully");
    }
}