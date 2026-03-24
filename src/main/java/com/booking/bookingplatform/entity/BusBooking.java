package com.booking.bookingplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_bookings")
public class BusBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;   // 🔥 MUST EXIST

    private String pnr;

    private double amount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(unique = true)
    private String bookingCode;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters

    public Long getId() { return id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getSeatNumber() {     // 🔥 MUST EXIST
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
}
