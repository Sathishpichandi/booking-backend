package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.dto.BookingSummaryDTO;
import com.booking.bookingplatform.service.BookingSummaryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingSummaryController {

    private final BookingSummaryService service;

    public BookingSummaryController(BookingSummaryService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public BookingSummaryDTO getSummary(Authentication authentication) {

        String email = authentication.getName();  // logged-in user's email

        return service.getSummary(email);
    }
}