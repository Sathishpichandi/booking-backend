package com.booking.bookingplatform.service;

import com.booking.bookingplatform.entity.*;
import com.booking.bookingplatform.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final HotelBookingRepository hotelBookingRepository;
    private final TrainBookingRepository trainBookingRepository;
    private final BusBookingRepository busBookingRepository;
    private final CarBookingRepository carBookingRepository;


    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository,
                          HotelBookingRepository hotelBookingRepository,
                          TrainBookingRepository trainBookingRepository,
                          BusBookingRepository busBookingRepository,
                          CarBookingRepository carBookingRepository
    ) {

        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.hotelBookingRepository = hotelBookingRepository;
        this.trainBookingRepository = trainBookingRepository;
        this.busBookingRepository = busBookingRepository;
        this.carBookingRepository = carBookingRepository;
    }

    @Transactional
    public Payment makePayment(Long referenceId,
                               PaymentReferenceType type,
                               BigDecimal amount,
                               PaymentMethod method) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid payment amount");
        }

        // 🔥 Create payment object
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(generateTransactionId());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setReferenceId(referenceId);
        payment.setReferenceType(type);

        switch (type) {

            case FLIGHT -> handleFlightPayment(referenceId);

            case HOTEL -> handleHotelPayment(referenceId);

            case TRAIN -> handleTrainPayment(referenceId);

            case BUS -> handleBusPayment(referenceId);

            case CAR -> handleCarPayment(referenceId);


            default -> throw new RuntimeException("Unsupported booking type");
        }

        return paymentRepository.save(payment);
    }

    // ================= FLIGHT PAYMENT =================

    private void handleFlightPayment(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Flight booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING &&
                booking.getStatus() != BookingStatus.CREATED) {
            throw new RuntimeException("Invalid flight booking state");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }

    // ================= HOTEL PAYMENT =================

    private void handleHotelPayment(Long bookingId) {

        HotelBooking booking = hotelBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Hotel booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException("Invalid hotel booking state");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        hotelBookingRepository.save(booking);
    }

    // ================= TRAIN PAYMENT =================

    private void handleTrainPayment(Long bookingId) {

        TrainBooking booking = trainBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Train booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException("Invalid train booking state");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        trainBookingRepository.save(booking);
    }
    private void handleBusPayment(Long bookingId) {

        BusBooking booking = busBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Bus booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING)
            throw new RuntimeException("Invalid bus booking state");

        booking.setStatus(BookingStatus.CONFIRMED);
        busBookingRepository.save(booking);
    }
    private void handleCarPayment(Long bookingId) {

        CarBooking booking = carBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Car booking not found"));

        if (booking.getStatus() != BookingStatus.PAYMENT_PENDING) {
            throw new RuntimeException("Invalid car booking state");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        carBookingRepository.save(booking);
    }



    // ================= UTILITY =================

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}
