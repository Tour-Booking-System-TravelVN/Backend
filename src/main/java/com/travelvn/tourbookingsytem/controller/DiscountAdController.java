package com.travelvn.tourbookingsytem.controller;

import com.travelvn.tourbookingsytem.dto.request.DiscountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountAdResponse;
import com.travelvn.tourbookingsytem.service.DiscountAdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountAdController {

    @Autowired
    private DiscountAdService discountAdService;

    @GetMapping
    public ResponseEntity<List<DiscountAdResponse>> getAllDiscounts() {
        return ResponseEntity.ok(discountAdService.getAllDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountAdResponse> getDiscountById(@PathVariable Integer id) {
        DiscountAdResponse discount = discountAdService.getDiscountById(id);
        return discount != null ? ResponseEntity.ok(discount) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DiscountAdResponse> createDiscount(@Valid @RequestBody DiscountAdRequest discountAdRequest) {
        return ResponseEntity.ok(discountAdService.createDiscount(discountAdRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountAdResponse> updateDiscount(@PathVariable Integer id, @Valid @RequestBody DiscountAdRequest discountAdRequest) {
        DiscountAdResponse updatedDiscount = discountAdService.updateDiscount(id, discountAdRequest);
        return updatedDiscount != null ? ResponseEntity.ok(updatedDiscount) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        discountAdService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}