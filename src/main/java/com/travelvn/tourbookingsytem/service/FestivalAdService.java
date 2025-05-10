package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.FestivalAdRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalAdResponse;
import com.travelvn.tourbookingsytem.mapper.FestivalAdMapper;
import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FestivalAdService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private FestivalAdMapper festivalAdMapper;

    public List<FestivalAdResponse> getAllFestivals() {
        return festivalRepository.findAll().stream()
                .map(festivalAdMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FestivalAdResponse getFestivalById(Integer id) {
        Optional<Festival> festival = festivalRepository.findById(id);
        return festival.map(festivalAdMapper::toResponse).orElse(null);
    }

    public FestivalAdResponse createFestival(FestivalAdRequest festivalAdRequest) {
        Festival festival = festivalAdMapper.toEntity(festivalAdRequest);
        festival.setId(null); // Đảm bảo ID tự sinh
        Optional<Festival> existingFestival = festivalRepository.findByFestivalName(festival.getFestivalName());
        if (existingFestival.isPresent()) {
            throw new RuntimeException("Festival exists: " + festival.getFestivalName());
        }
        Festival savedFestival = festivalRepository.save(festival);
        return festivalAdMapper.toResponse(savedFestival);
    }

    public FestivalAdResponse updateFestival(Integer id, FestivalAdRequest festivalAdRequest) {
        Optional<Festival> festivalOptional = festivalRepository.findById(id);
        if (festivalOptional.isPresent()) {
            Festival festival = festivalOptional.get();
            festivalAdMapper.updateEntityFromRequest(festivalAdRequest, festival);
            Festival updatedFestival = festivalRepository.save(festival);
            return festivalAdMapper.toResponse(updatedFestival);
        }
        return null;
    }

    public void deleteFestival(Integer id) {
        festivalRepository.deleteById(id);
    }
}