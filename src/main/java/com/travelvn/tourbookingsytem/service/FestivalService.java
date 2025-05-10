package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.request.FestivalRequest;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.mapper.FestivalMapper;
import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private FestivalMapper festivalMapper;

    public List<FestivalResponse> getAllFestivals() {
        return festivalRepository.findAll().stream()
                .map(festivalMapper::toResponse)
                .collect(Collectors.toList());
    }

    public FestivalResponse getFestivalById(Integer id) {
        Optional<Festival> festival = festivalRepository.findById(id);
        return festival.map(festivalMapper::toResponse).orElse(null);
    }

    public FestivalResponse createFestival(FestivalRequest festivalRequest) {
        Festival festival = festivalMapper.toEntity(festivalRequest);
        festival.setId(null); // Đảm bảo ID tự sinh
        Optional<Festival> existingFestival = festivalRepository.findByFestivalName(festival.getFestivalName());
        if (existingFestival.isPresent()) {
            throw new RuntimeException("Festival exists: " + festival.getFestivalName());
        }
        Festival savedFestival = festivalRepository.save(festival);
        return festivalMapper.toResponse(savedFestival);
    }

    public FestivalResponse updateFestival(Integer id, FestivalRequest festivalRequest) {
        Optional<Festival> festivalOptional = festivalRepository.findById(id);
        if (festivalOptional.isPresent()) {
            Festival festival = festivalOptional.get();
            festivalMapper.updateEntityFromRequest(festivalRequest, festival);
            Festival updatedFestival = festivalRepository.save(festival);
            return festivalMapper.toResponse(updatedFestival);
        }
        return null;
    }

    public void deleteFestival(Integer id) {
        festivalRepository.deleteById(id);
    }
}