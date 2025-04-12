package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
// da test
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
        festival.setId(null);
        //test them pham exception( do festival name equel
        Optional<Festival> existingFestival = festivalRepository.findByFestivalName(festival.getFestivalName());
        if (existingFestival.isPresent()) {
            throw new RuntimeException("Festival exists: " + festival.getFestivalName());
        }
        return festivalRepository.save(festival);
    }
//cap nhat festival
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
// xoas
    public void deleteFestival(Integer id) {
        festivalRepository.deleteById(id);
    }
}