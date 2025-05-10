package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.service.ReportAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportAdController {

    @Autowired
    private ReportAdService reportAdService;

    // Số lượt đặt tour (Sales Card)
    @GetMapping("/sales")
    public ResponseEntity<Map<String, Object>> getSalesReport(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        Map<String, Object> data = reportAdService.getSalesReport(filter);
        return ResponseEntity.ok(data);
    }

    // Doanh thu (Revenue Card)
    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> getRevenueReport(
            @RequestParam(value = "filter", defaultValue = "thisMonth") String filter) {
        Map<String, Object> data = reportAdService.getRevenueReport(filter);
        return ResponseEntity.ok(data);
    }

    // Khách hàng mới (Customers Card)
    @GetMapping("/customers")
    public ResponseEntity<Map<String, Object>> getCustomersReport(
            @RequestParam(value = "filter", defaultValue = "thisYear") String filter) {
        Map<String, Object> data = reportAdService.getCustomersReport(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo đặt tour theo khoảng thời gian (được tính theo ngày)
    @GetMapping("/booking")
    public ResponseEntity<Map<String, Object>> getBookingReport(
            @RequestParam(value = "filter", defaultValue = "thisWeek") String filter) {
        Map<String, Object> data = reportAdService.getBookingReport(filter);
        return ResponseEntity.ok(data);
    }

    // Danh sách các booking gần đây
    @GetMapping("/recent-bookings")
    public ResponseEntity<List<Map<String, Object>>> getRecentBookings(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        List<Map<String, Object>> data = reportAdService.getRecentBookings(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo tour bán chạy (top selling tours)
    @GetMapping("/top-selling-tours")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingTours(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        List<Map<String, Object>> data = reportAdService.getTopSellingTours(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo thời điểm đặt tour (website traffic)
    @GetMapping("/traffic")
    public ResponseEntity<List<Map<String, Object>>> getTrafficReport(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        List<Map<String, Object>> data = reportAdService.getTrafficReport(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo doanh thu (budget report)
    @GetMapping("/budget")
    public ResponseEntity<Map<String, Object>> getBudgetReport(
            @RequestParam(value = "filter", defaultValue = "thisMonth") String filter) {
        Map<String, Object> data = reportAdService.getBudgetReport(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo hoạt động gần đây (recent activity)
    @GetMapping("/activities")
    public ResponseEntity<List<Map<String, Object>>> getRecentActivities(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        List<Map<String, Object>> data = reportAdService.getRecentActivities(filter);
        return ResponseEntity.ok(data);
    }

    // Báo cáo Tin tức & Cập nhật
    @GetMapping("/news")
    public ResponseEntity<List<Map<String, Object>>> getNews(
            @RequestParam(value = "filter", defaultValue = "today") String filter) {
        List<Map<String, Object>> data = reportAdService.getNews(filter);
        return ResponseEntity.ok(data);
    }
}