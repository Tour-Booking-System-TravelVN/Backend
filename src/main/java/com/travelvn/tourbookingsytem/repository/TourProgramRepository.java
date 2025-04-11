package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.TourProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourProgramRepository extends JpaRepository<TourProgram, Integer> {
}
