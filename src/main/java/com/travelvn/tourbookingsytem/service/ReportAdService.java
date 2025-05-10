package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Booking;
import com.travelvn.tourbookingsytem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportAdService {

    @Autowired
    private BookingRepository bookingRepository;

    // Báo cáo số lượt đặt tour (Sales Card)
    public Map<String, Object> getSalesReport(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();

        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }

        Long count = bookingRepository.countBookingsByStatusAndTime("BOOKED", startTime, endTime);
        Map<String, Object> response = new HashMap<>();
        response.put("count", count);
        response.put("unit", "Lượt");
        return response;
    }

    // Báo cáo doanh thu (Revenue Card)
    public Map<String, Object> getRevenueReport(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();

        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }

        // Giả lập dữ liệu doanh thu (cần thay thế bằng logic thực tế từ database)
        List<Booking> bookings = bookingRepository.findBookingsByTimeRange(startTime, endTime);
        BigDecimal totalRevenue = BigDecimal.ZERO; // Start with zero as a BigDecimal
        for (Booking b : bookings) {
            if (b.getStatus().equals("BOOKED")) {
                BigDecimal totalAmount = b.getTotalAmount();
                totalRevenue = totalRevenue.add(totalAmount); // Use BigDecimal's add method
            }
        }
// Convert back to long if needed
        long finalRevenue = totalRevenue.longValue();

        // Giả lập phần trăm thay đổi (cần logic thực tế để so sánh với kỳ trước)
        int percentageChange = 8; // Ví dụ: 8% tăng
        String trend = "tăng";

        Map<String, Object> response = new HashMap<>();
        response.put("amount", totalRevenue);
        response.put("percentageChange", percentageChange);
        response.put("trend", trend);
        return response;
    }

    // Báo cáo khách hàng mới (Customers Card)
    public Map<String, Object> getCustomersReport(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();

        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }

        // Giả lập số lượng khách hàng mới
        List<Booking> bookings = bookingRepository.findBookingsByTimeRange(startTime, endTime);
        long customerCount = bookings.stream()
                .filter(b -> b.getStatus().equals("BOOKED"))
                .map(Booking::getC)
                .distinct()
                .count();

        int percentageChange = 12; // Ví dụ: 12% giảm
        String trend = "giảm";

        Map<String, Object> response = new HashMap<>();
        response.put("count", customerCount);
        response.put("percentageChange", percentageChange);
        response.put("trend", trend);
        return response;
    }

    // Báo cáo số lượt đặt, hủy theo ngày theo khoảng thời gian (theo filter)
    public Map<String, Object> getBookingReport(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();

        // Xác định thời gian bắt đầu dựa theo filter
        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisWeek":
            default:
                startTime = LocalDateTime.now().minusDays(6).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }

        // Lấy danh sách booking theo khoảng thời gian bằng cách sử dụng bookingDate
        List<Booking> bookings = bookingRepository.findBookingsByTimeRange(startTime, endTime);

        List<Long> bookingCounts = new ArrayList<>();
        List<Long> cancelCounts = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        // Giả định mỗi ngày có 86400 giây, duyệt từ startTime đến endTime theo bước 1 ngày
        for (Instant date = startTime; date.isBefore(endTime.plusSeconds(1)); date = date.plusSeconds(86400)) {
            Instant dayEnd = date.plusSeconds(86399);
            // Sử dụng query đã sửa ở repository (sử dụng bookingDate thay vì createdTime)
            bookingCounts.add(bookingRepository.countBookingsByStatusAndTime("BOOKED", date, dayEnd));
            cancelCounts.add(bookingRepository.countBookingsByStatusAndTime("CANCELLED", date, dayEnd));
            // Định dạng ngày theo định dạng datetime mà ApexCharts yêu cầu
            String dayLabel = date.toString(); // Định dạng ISO 8601
            categories.add(dayLabel);
        }

        Map<String, Object> response = new HashMap<>();

        Map<String, Object> bookingSeries = new HashMap<>();
        bookingSeries.put("name", "Số lượt đặt");
        bookingSeries.put("data", bookingCounts);

        Map<String, Object> cancelSeries = new HashMap<>();
        cancelSeries.put("name", "Số lượt hủy");
        cancelSeries.put("data", cancelCounts);

        List<Map<String, Object>> series = new ArrayList<>();
        series.add(bookingSeries);
        series.add(cancelSeries);

        response.put("series", series);
        response.put("categories", categories);
        return response;
    }

    // Lấy danh sách các booking gần đây theo khoảng thời gian
    public List<Map<String, Object>> getRecentBookings(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();
        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }
        List<Booking> bookings = bookingRepository.findBookingsByTimeRange(startTime, endTime);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Booking booking : bookings) {
            Map<String, Object> bookingData = new HashMap<>();
            bookingData.put("id", booking.getBookingId());
            bookingData.put("customerName", booking.getC()); // Thay getC() bằng getCustomerName()
            bookingData.put("tourCode", booking.getTourUnit().getTour().getTourId()); // Thay tourId thành tourCode để khớp với frontend
            bookingData.put("price", booking.getTotalAmount());
            bookingData.put("status", booking.getStatus().equals("BOOKED") ? "Đặt thành công" : "Hủy thành công");
            response.add(bookingData);
        }
        return response;
    }

    // Báo cáo tour bán chạy
    public List<Map<String, Object>> getTopSellingTours(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();
        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }
        List<Object[]> topTours = bookingRepository.findTopSellingTours(startTime, endTime);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] tour : topTours) {
            Map<String, Object> tourData = new HashMap<>();
            tourData.put("tourCode", tour[0]); // Thay tourId thành tourCode để khớp với frontend
            // Giả sử tour[1] là số lượt đặt, tour[2] là doanh thu
            tourData.put("bookingCount", tour[1]); // Số lượt đặt
            tourData.put("revenue", tour[2]); // Doanh thu
            // Cần lấy giá trung bình hoặc giá cố định của tour (giả sử lấy từ doanh thu/số lượt)
            long price = (Long) tour[2] / (Long) tour[1]; // Doanh thu / số lượt
            tourData.put("price", price); // Thêm giá để khớp với frontend
            response.add(tourData);
        }
        return response;
    }

    // Báo cáo hoạt động gần đây
    public List<Map<String, Object>> getRecentActivities(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();
        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }
        List<Booking> bookings = bookingRepository.findBookingsByTimeRange(startTime, endTime);
        List<Map<String, Object>> response = new ArrayList<>();
        for (Booking booking : bookings) {
            Map<String, Object> activity = new HashMap<>();
            long minutesDiff = (Instant.now().toEpochMilli() - booking.getBookingDate().toEpochMilli()) / (1000 * 60);
            String timeLabel;
            if (minutesDiff < 60) {
                timeLabel = minutesDiff + " min";
            } else if (minutesDiff < 1440) {
                timeLabel = (minutesDiff / 60) + " hrs";
            } else if (minutesDiff < 10080) {
                timeLabel = (minutesDiff / 1440) + " days";
            } else {
                timeLabel = (minutesDiff / 10080) + " weeks";
            }
            activity.put("timeLabel", timeLabel);
            activity.put("customerName", booking.getC()); // Thay getC() bằng getCustomerName()
            activity.put("tourCode", booking.getTourUnit().getTour().getTourId()); // Thay tourId thành tourCode
            activity.put("status", booking.getStatus().equals("BOOKED") ? "success" : "danger");
            response.add(activity);
        }
        return response;
    }

    // Báo cáo doanh thu (budget report) - giả lập dữ liệu
    public Map<String, Object> getBudgetReport(String filter) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> plan = new HashMap<>();
        plan.put("name", "Plan");
        plan.put("value", new long[]{4200, 3000, 20000, 35000, 50000, 18000});
        Map<String, Object> real = new HashMap<>();
        real.put("name", "Real");
        real.put("value", new long[]{5000, 14000, 28000, 26000, 42000, 21000});
        series.add(plan);
        series.add(real);

        List<Map<String, Object>> indicators = new ArrayList<>();
        indicators.add(Map.of("name", "Văn hóa", "max", 6500));
        indicators.add(Map.of("name", "Ẩm thực", "max", 16000));
        indicators.add(Map.of("name", "Nghỉ dưỡng", "max", 30000));
        indicators.add(Map.of("name", "Mạo hiểm", "max", 38000));
        indicators.add(Map.of("name", "Thiên nhiên", "max", 52000));

        response.put("series", series);
        response.put("indicators", indicators);
        return response;
    }

    // Báo cáo lưu lượng truy cập (traffic report)
    public List<Map<String, Object>> getTrafficReport(String filter) {
        Instant startTime;
        Instant endTime = Instant.now();
        switch (filter) {
            case "today":
                startTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisMonth":
                startTime = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            case "thisYear":
                startTime = LocalDateTime.now().withDayOfYear(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
            default:
                startTime = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0)
                        .atZone(ZoneId.systemDefault()).toInstant();
                break;
        }

        List<Map<String, Object>> response = new ArrayList<>();
        response.add(Map.of("value", bookingRepository.countBookingsByHourRange(startTime, endTime, 5, 12), "name", "5h - 12h"));
        response.add(Map.of("value", bookingRepository.countBookingsByHourRange(startTime, endTime, 12, 17), "name", "12h - 17h"));
        response.add(Map.of("value", bookingRepository.countBookingsByHourRange(startTime, endTime, 17, 22), "name", "17h - 22h"));
        response.add(Map.of("value", bookingRepository.countBookingsByHourRange(startTime, endTime, 22, 4), "name", "22h - 5h"));
        return response;
    }

    // Phương thức báo cáo Tin tức & Cập nhật (ở đây trả về danh sách trống)
    public List<Map<String, Object>> getNews(String filter) {
        return new ArrayList<>();
    }
}