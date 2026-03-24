package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);
}
