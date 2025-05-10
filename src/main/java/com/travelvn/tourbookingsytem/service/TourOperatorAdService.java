package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.TourOperatorAdRequest;
import com.travelvn.tourbookingsytem.dto.response.TourOperatorAdResponse;
import com.travelvn.tourbookingsytem.mapper.TourOperatorMapper;
import com.travelvn.tourbookingsytem.model.TourOperator;
import com.travelvn.tourbookingsytem.repository.TourOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourOperatorAdService {

    @Autowired
    private TourOperatorRepository tourOperatorRepository;

    @Autowired
    private TourOperatorMapper tourOperatorMapper;

    @Autowired
    private UserAccountFactoryService userAccountFactoryService;



    public List<TourOperatorAdResponse> getAllTourOperators() {
        return tourOperatorRepository.findAll().stream()
                .map(tourOperatorMapper::toResponse)
                .collect(Collectors.toList());
    }

    public TourOperatorAdResponse findTourOperatorById(int id) {
        Optional<TourOperator> tourOperator = tourOperatorRepository.findById(id);
        return tourOperator.map(tourOperatorMapper::toResponse).orElse(null);
    }

    public TourOperatorAdResponse createTourOperator(TourOperatorAdRequest tourOperatorAdRequest) {
        TourOperator tourOperator = tourOperatorMapper.toEntity(tourOperatorAdRequest);
        tourOperator.setId(null); // Đảm bảo ID tự sinh
        TourOperator savedTourOperator = tourOperatorRepository.save(tourOperator);
        userAccountFactoryService.createForTourOperator(savedTourOperator);
        return tourOperatorMapper.toResponse(savedTourOperator);
    }

    public TourOperatorAdResponse updateTourOperator(Integer id, TourOperatorAdRequest tourOperatorAdRequest) {
        Optional<TourOperator> tourOperatorOptional = tourOperatorRepository.findById(id);
        if (tourOperatorOptional.isPresent()) {
            TourOperator tourOperator = tourOperatorOptional.get();
            tourOperatorMapper.updateEntityFromRequest(tourOperatorAdRequest, tourOperator);
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