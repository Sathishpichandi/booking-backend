package com.booking.bookingplatform.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String brand;

    private double pricePerDay;

    private int availableCars;

    @Enumerated(EnumType.STRING)
    private CarType carType;



    // Getters & Setters

    public Long getId() { return id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public int getAvailableCars() { return availableCars; }
    public void setAvailableCars(int availableCars) { this.availableCars = availableCars; }

    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }
}
