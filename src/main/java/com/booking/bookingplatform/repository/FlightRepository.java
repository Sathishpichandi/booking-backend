package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findBySourceIgnoreCaseAndDestinationIgnoreCase(
            String source,
            String destination
    );
}
