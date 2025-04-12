package com.travelvn.tourbookingsytem.controller;


import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.service.DiscountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// test full
@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    @Autowired
    private DiscountService discountService;
// laasy full danh saschg
    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }
//lay theo id
    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Integer id) {
        Discount discount = discountService.getDiscountById(id);
        if (discount != null) {
            return ResponseEntity.ok(discount);
        }
        return ResponseEntity.notFound().build();
    }
// them 1 moiws
    @PostMapping
    public ResponseEntity<Discount> createDiscount(@Valid @RequestBody Discount discount) {
        return ResponseEntity.ok(discountService.createDiscount(discount));
    }
//sua theo id
    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@PathVariable Integer id, @Valid @RequestBody Discount discountDetails) {
        Discount updatedDiscount = discountService.updateDiscount(id, discountDetails);
        if (updatedDiscount != null) {
            return ResponseEntity.ok(updatedDiscount);
        }
        return ResponseEntity.notFound().build();
    }
// xoas theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Integer id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}