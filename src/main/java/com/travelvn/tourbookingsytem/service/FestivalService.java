package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestivalService {

    @Autowired
    private FestivalRepository festivalRepository;

    public List<Festival> getAllFestivals() {
        return festivalRepository.findAll();
    }

    public Festival getFestivalById(Integer id) {
        Optional<Festival> festival = festivalRepository.findById(id);
        return festival.orElse(null);
    }
//tạo festival mới
    public Festival createFestival(Festival festival) {
        return festivalRepository.save(festival);
    }

    public Festival updateFestival(Integer id, Festival festivalDetails) {
        Optional<Festival> festivalOptional = festivalRepository.findById(id);
        if (festivalOptional.isPresent()) {
            Festival festival = festivalOptional.get();
            festival.setFestivalName(festivalDetails.getFestivalName());
            festival.setDescription(festivalDetails.getDescription());
            festival.setDisplayStatus(festivalDetails.getDisplayStatus());
            return festivalRepository.save(festival);
        }
        return null;
    }

    public void deleteFestival(Integer id) {
        festivalRepository.deleteById(id);
    }
}