package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.DiscountRequest;
import com.travelvn.tourbookingsytem.dto.response.DiscountResponse;
import com.travelvn.tourbookingsytem.mapper.DiscountMapper;
import com.travelvn.tourbookingsytem.model.Discount;
import com.travelvn.tourbookingsytem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountMapper discountMapper;

    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(discountMapper::toResponse)
                .collect(Collectors.toList());
    }

    public DiscountResponse getDiscountById(Integer id) {
        Optional<Discount> discount = discountRepository.findById(id);
        return discount.map(discountMapper::toResponse).orElse(null);
    }

    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        Discount discount = discountMapper.toEntity(discountRequest);
        discount.setId(null); // Đảm bảo ID tự sinh
        Discount savedDiscount = discountRepository.save(discount);
        return discountMapper.toResponse(savedDiscount);
    }

    public DiscountResponse updateDiscount(Integer id, DiscountRequest discountRequest) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if (discountOptional.isPresent()) {
            Discount discount = discountOptional.get();
            discountMapper.updateEntityFromRequest(discountRequest, discount);
            Discount updatedDiscount = discountRepository.save(discount);
            return discountMapper.toResponse(updatedDiscount);
        }
        return null;
    }

    public void deleteDiscount(Integer id) {
        discountRepository.deleteById(id);
    }
}