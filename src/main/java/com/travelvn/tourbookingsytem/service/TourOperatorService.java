package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourOperatorRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorResponse;
import com.travelvn.tourbookingsytem.mapper.TourOperatorMapper;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourOperatorService {

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Autowired
    private TourOperatorMapper tourOperatorMapper;

    @Autowired
    private UserAccountFactoryService userAccountFactoryService;



    public List<TourOperatorResponse> getAllTourOperators() {
        return tourOperatorRepository.findAll().stream()
                .map(tourOperatorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TourOperatorResponse findTourOperatorById(int id) {
        Optional<TourOperator> tourOperator = tourOperatorRepository.findById(id);
        return tourOperator.map(tourOperatorMapper::toResponse).orElse(null);
    }

    public TourOperatorResponse createTourOperator(TourOperatorRequest tourOperatorRequest) {
        TourOperator tourOperator = tourOperatorMapper.toEntity(tourOperatorRequest);
        tourOperator.setId(null); // Đảm bảo ID tự sinh
        TourOperator savedTourOperator = tourOperatorRepository.save(tourOperator);
        userAccountFactoryService.createForTourOperator(savedTourOperator);
        return tourOperatorMapper.toResponse(savedTourOperator);
    }

    public TourOperatorResponse updateTourOperator(Integer id, TourOperatorRequest tourOperatorRequest) {
        Optional<TourOperator> tourOperatorOptional = tourOperatorRepository.findById(id);
        if (tourOperatorOptional.isPresent()) {
            TourOperator tourOperator = tourOperatorOptional.get();
            tourOperatorMapper.updateEntityFromRequest(tourOperatorRequest, tourOperator);
            TourOperator updatedTourOperator = tourOperatorRepository.save(tourOperator);
            return tourOperatorMapper.toResponse(updatedTourOperator);
        }
        return null;
    }

    public void deleteTourOperator(Integer id) {
        if (!tourOperatorRepository.existsById(id)) {
            throw new RuntimeException("Tour Operator with ID " + id + " not found");
        }
        tourOperatorRepository.deleteById(id);
    }
}