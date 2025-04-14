package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.lite.CompanionCustomerResponseLite;
import com.travelvn.tourbookingsytem.mapper.CompanionCustomerMapper;
import com.travelvn.tourbookingsytem.repository.CompanionCustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanionCustomerService {
    private final CompanionCustomerRepository companionCustomerRepository;
    private final CompanionCustomerMapper companionCustomerMapper;

//    public List<CompanionCustomerResponseLite> getCompanionCustomerWithMeByTourUnitId(String tourUnitId) {
//        return null;
//    }
}
