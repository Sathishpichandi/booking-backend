package com.booking.bookingplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trains")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;

    private double slPrice;
    private double threeAPrice;
    private double twoAPrice;

    private int slAvailableSeats;
    private int threeAAvailableSeats;
    private int twoAAvailableSeats;

    // Getters & Setters
    public Long getId() { return id; }

    public String getTrainNumber() { return trainNumber; }
    public void setTrainNumber(String trainNumber) { this.trainNumber = trainNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public double getSlPrice() { return slPrice; }
    public void setSlPrice(double slPrice) { this.slPrice = slPrice; }

    public double getThreeAPrice() { return threeAPrice; }
    public void setThreeAPrice(double threeAPrice) { this.threeAPrice = threeAPrice; }

    public double getTwoAPrice() { return twoAPrice; }
    public void setTwoAPrice(double twoAPrice) { this.twoAPrice = twoAPrice; }

    public int getSlAvailableSeats() { return slAvailableSeats; }
    public void setSlAvailableSeats(int slAvailableSeats) { this.slAvailableSeats = slAvailableSeats; }

    public int getThreeAAvailableSeats() { return threeAAvailableSeats; }
    public void setThreeAAvailableSeats(int threeAAvailableSeats) { this.threeAAvailableSeats = threeAAvailableSeats; }

    public int getTwoAAvailableSeats() { return twoAAvailableSeats; }
    public void setTwoAAvailableSeats(int twoAAvailableSeats) { this.twoAAvailableSeats = twoAAvailableSeats; }
}
