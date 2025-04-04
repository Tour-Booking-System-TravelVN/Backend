package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummaryReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("completedBookings", reportService.getCompletedBookings());
        report.put("uniqueCustomers", reportService.getUniqueCustomers());
        report.put("totalRevenue", reportService.getTotalRevenue());
        report.put("cancelledBookings", reportService.getCancelledBookings());
        return ResponseEntity.ok(report);
    }
}