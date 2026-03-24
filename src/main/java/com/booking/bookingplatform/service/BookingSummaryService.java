package com.booking.bookingplatform.service;

import com.booking.bookingplatform.dto.BookingSummaryDTO;
import com.booking.bookingplatform.entity.BookingStatus;
import com.booking.bookingplatform.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookingSummaryService {

    private final BookingRepository flightRepo;
    private final BusBookingRepository busRepo;
    private final TrainBookingRepository trainRepo;
    private final HotelBookingRepository hotelRepo;
    private final CarBookingRepository carRepo;

    public BookingSummaryService(
            BookingRepository flightRepo,
            BusBookingRepository busRepo,
            TrainBookingRepository trainRepo,
            HotelBookingRepository hotelRepo,
            CarBookingRepository carRepo) {

        this.flightRepo = flightRepo;
        this.busRepo = busRepo;
        this.trainRepo = trainRepo;
        this.hotelRepo = hotelRepo;
        this.carRepo = carRepo;
    }

    public BookingSummaryDTO getSummary(String email) {

        long upcoming =
                flightRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED) +
                        busRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED) +
                        trainRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED) +
                        hotelRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED) +
                        carRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED);

        long completed =
                flightRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED)
                        + busRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED)
                        + trainRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED)
                        + hotelRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED)
                        + carRepo.countByUser_EmailAndStatus(email, BookingStatus.CONFIRMED);

        long cancelled =
                flightRepo.countByUser_EmailAndStatus(email, BookingStatus.CANCELLED) +
                        busRepo.countByUser_EmailAndStatus(email, BookingStatus.CANCELLED) +
                        trainRepo.countByUser_EmailAndStatus(email, BookingStatus.CANCELLED) +
                        hotelRepo.countByUser_EmailAndStatus(email, BookingStatus.CANCELLED) +
                        carRepo.countByUser_EmailAndStatus(email, BookingStatus.CANCELLED);

        BigDecimal totalSpent = BigDecimal.valueOf(

                flightRepo.totalSpent(email) +
                        busRepo.totalSpent(email) +
                        trainRepo.totalSpent(email) +
                        hotelRepo.totalSpent(email) +
                        carRepo.totalSpent(email)

        );

        return new BookingSummaryDTO(upcoming, completed, cancelled, totalSpent);
    }
}