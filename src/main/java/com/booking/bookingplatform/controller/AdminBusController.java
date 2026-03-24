package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Bus;
import com.booking.bookingplatform.repository.BusRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/api/admin/buses")
public class AdminBusController {

    private final BusRepository busRepository;

    public AdminBusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // ✅ GET ALL BUSES  ← THIS WAS MISSING
    @GetMapping
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // ✅ CREATE BUS
    @PostMapping
    public Bus createBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }

    // ✅ UPDATE BUS
    @PutMapping("/{id}")
    public Bus updateBus(@PathVariable Long id,
                         @RequestBody Bus updated) {

        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        bus.setBusNumber(updated.getBusNumber());
        bus.setOperatorName(updated.getOperatorName());
        bus.setSource(updated.getSource());
        bus.setDestination(updated.getDestination());
        bus.setBusType(updated.getBusType());
        bus.setSeatType(updated.getSeatType());
        bus.setPrice(updated.getPrice());
        bus.setAvailableSeats(updated.getAvailableSeats());

        return busRepository.save(bus);
    }

    // ✅ DELETE BUS
    @DeleteMapping("/{id}")
    public String deleteBus(@PathVariable Long id) {
        busRepository.deleteById(id);
        return "Bus deleted successfully";
    }
}