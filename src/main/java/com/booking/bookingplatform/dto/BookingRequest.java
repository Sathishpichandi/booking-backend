package com.booking.bookingplatform.dto;

import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }
}
