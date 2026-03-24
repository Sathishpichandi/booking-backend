package com.booking.bookingplatform.repository;

import com.booking.bookingplatform.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
        SELECT h FROM Hotel h
        WHERE (:city IS NULL OR LOWER(h.city) = LOWER(:city))
        AND (:minRating IS NULL OR h.rating >= :minRating)
    """)
    List<Hotel> searchHotels(@Param("city") String city,
                             @Param("minRating") Double minRating);
}
