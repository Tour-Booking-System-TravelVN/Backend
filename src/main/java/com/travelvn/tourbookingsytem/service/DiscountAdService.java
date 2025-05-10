package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.DiscountAdRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountAdResponse;
import com.travelvn.tourbookingsytem.mapper.DiscountAdMapper;
import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountAdService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountAdMapper discountAdMapper;

    public List<DiscountAdResponse> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(discountAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DiscountAdResponse getDiscountById(Integer id) {
        Optional<Discount> discount = discountRepository.findById(id);
        return discount.map(discountAdMapper::toResponse).orElse(null);
    }

    public DiscountAdResponse createDiscount(DiscountAdRequest discountAdRequest) {
        Discount discount = discountAdMapper.toEntity(discountAdRequest);
        discount.setId(null); // Đảm bảo ID tự sinh
        Discount savedDiscount = discountRepository.save(discount);
        return discountAdMapper.toResponse(savedDiscount);
    }

    public DiscountAdResponse updateDiscount(Integer id, DiscountAdRequest discountAdRequest) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isPresent()) {
            Discount discount = discountOptional.get();
            discountAdMapper.updateEntityFromRequest(discountAdRequest, discount);
            Discount updatedDiscount = discountRepository.save(discount);
            return discountAdMapper.toResponse(updatedDiscount);
        }
        return null;
    }

    public void deleteDiscount(Integer id) {
        discountRepository.deleteById(id);
    }
}