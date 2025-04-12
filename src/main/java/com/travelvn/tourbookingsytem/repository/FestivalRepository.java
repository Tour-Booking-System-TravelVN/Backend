package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival, Integer> {
    Optional<Festival> findByFestivalName(String festivalName);
}
