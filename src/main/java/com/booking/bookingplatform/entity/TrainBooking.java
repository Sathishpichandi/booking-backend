package com.booking.bookingplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "train_bookings")
public class TrainBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrainClassType classType;

    private double amount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(unique = true)
    private String bookingCode;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters
    public Long getId() { return id; }

    public TrainClassType getClassType() { return classType; }
    public void setClassType(TrainClassType classType) { this.classType = classType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public Train getTrain() { return train; }
    public void setTrain(Train train) { this.train = train; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
