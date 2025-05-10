package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.DiscountRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountResponse;
import com.travelvn.tourbookingsytem.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable Integer id) {
        DiscountResponse discount = discountService.getDiscountById(id);
        return discount != null ? ResponseEntity.ok(discount) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest discountRequest) {
        return ResponseEntity.ok(discountService.createDiscount(discountRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountResponse> updateDiscount(@PathVariable Integer id, @Valid @RequestBody DiscountRequest discountRequest) {
        DiscountResponse updatedDiscount = discountService.updateDiscount(id, discountRequest);
        return updatedDiscount != null ? ResponseEntity.ok(updatedDiscount) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}