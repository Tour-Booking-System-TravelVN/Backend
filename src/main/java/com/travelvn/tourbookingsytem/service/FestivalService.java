package com.travelvn.tourbookingsytem.service;

import com.travelvn.tourbookingsytem.dto.response.ApiResponse;
import com.travelvn.tourbookingsytem.dto.response.FestivalResponse;
import com.travelvn.tourbookingsytem.exception.AppException;
import com.travelvn.tourbookingsytem.exception.ErrorCode;
import com.travelvn.tourbookingsytem.mapper.FestivalMapper;
import com.travelvn.tourbookingsytem.model.Festival;
import com.travelvn.tourbookingsytem.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final FestivalMapper festivalMapper;

    /**
     * Lấy tên các lễ hội được hiển thị trên index
     * @return
     */
    public List<FestivalResponse> getFestivalsCarousel() {
        List<Festival> festivals = festivalRepository.findAllByDisplayStatusEquals(true);
        if(festivals.isEmpty())
            throw new AppException(ErrorCode.NO_FESTIVALS_CAROUSEL);
        return festivals.stream().map(festivalMapper::toFestivalResponseCarousel).collect(Collectors.toList());
    }
}
