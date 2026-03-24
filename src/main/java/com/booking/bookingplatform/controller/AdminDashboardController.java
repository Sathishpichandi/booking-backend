package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.dto.AdminDashboardResponse;
import com.booking.bookingplatform.service.AdminDashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public AdminDashboardResponse getDashboard() {
        return dashboardService.getDashboardData();
    }
}
