package com.booking.bookingplatform.dto;

public class BookingResponse {

    private Long bookingId;
    private String status;
    private FlightResponse flight;
    private String pnr;

    public BookingResponse(Long bookingId,
                           String pnr,
                           String status,
                           FlightResponse flight) {
        this.bookingId = bookingId;
        this.pnr = pnr;
        this.status = status;
        this.flight = flight;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public String getPnr() {
        return pnr;
    }   // 👈 ADD

    public String getStatus() {
        return status;
    }

    public FlightResponse getFlight() {
        return flight;
    }
}