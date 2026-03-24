package com.booking.bookingplatform.dto;

public class FlightResponse {

    private Long id;
    private String source;
    private String destination;
    private double price;

    public FlightResponse(Long id, String source,
                          String destination, double price) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public double getPrice() { return price; }
}
