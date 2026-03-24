package com.booking.bookingplatform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Use ENUM instead of String
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // ✈ Relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ✈ Relationship with Flight
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    // 🔥 Booking Details
    private String seatNumber;

    private int baggageKg;

    private String mealOption;

    @Column(unique = true, nullable = false)
    private String pnr;

    private LocalDateTime bookingTime;

    // 💳 Relationship with Payment


    // ================= CONSTRUCTOR =================
    public Booking() {
        this.bookingTime = LocalDateTime.now();
        this.status = BookingStatus.CREATED;
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getBaggageKg() {
        return baggageKg;
    }

    public void setBaggageKg(int baggageKg) {
        this.baggageKg = baggageKg;
    }

    public String getMealOption() {
        return mealOption;
    }

    public void setMealOption(String mealOption) {
        this.mealOption = mealOption;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }



}
