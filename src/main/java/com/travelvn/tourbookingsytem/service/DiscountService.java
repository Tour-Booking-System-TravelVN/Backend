package com.travelvn.tourbookingsytem.service;


import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;
//Layas
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscountById(Integer id) {
        Optional<Discount> discount = discountRepository.findById(id);
        return discount.orElse(null);
    }
//theem cap nhat
    public Discount createDiscount(Discount discount) {
        discount.setId(null);
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Integer id, Discount discountDetails) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isPresent()) {
            Discount discount = discountOptional.get();
            discount.setDiscountName(discountDetails.getDiscountName());
            discount.setDiscountValue(discountDetails.getDiscountValue());
            discount.setDiscountUnit(discountDetails.getDiscountUnit());
            return discountRepository.save(discount);
        }
        return null;
    }

    public void deleteDiscount(Integer id) {
        discountRepository.deleteById(id);
    }
}