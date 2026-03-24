package com.booking.bookingplatform.entity;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String source;
    private String destination;
    private double price;

    private LocalDate departureDate;
    private int availableSeats;
    private String flightType;


    public Flight() {}

    public Flight(String source, String destination, double price) {
        this.source = source;
        this.destination = destination;
        this.price = price;
    }
    public Long getId() { return id; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getFlightType() { return flightType; }
    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }
}
