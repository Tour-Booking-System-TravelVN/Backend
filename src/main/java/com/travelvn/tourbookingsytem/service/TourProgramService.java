package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.TourProgramResponse;
import com.travelvn.tourbookingsytem.mapper.TourProgramMapper;
import com.travelvn.tourbookingsytem.model.TourProgram;
import com.travelvn.tourbookingsytem.repository.TourProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourProgramService {

    private final TourProgramRepository tourProgramRepository;
    private final TourProgramMapper tourProgramMapper;

    /**
     * Lấy danh sách chương trình tour theo tourid
     * @param tourid
     * @return Danh sách chương trình tour theo tourid
     */
    public List<TourProgramResponse> getTourProgramByTour(String tourid) {
        List<TourProgram> tourPrograms = tourProgramRepository.findAllByTour_TourIdEqualsIgnoreCaseOrderByDay(tourid);
        return tourPrograms.stream().map(tourProgramMapper::toTourProgramResponse).collect(Collectors.toList());
    }
}
