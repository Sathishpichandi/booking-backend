package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Car;

import com.booking.bookingplatform.repository.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/search")
    public List<Car> searchCars(@RequestParam String city) {
        return carRepository.findByCityIgnoreCase(city);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
