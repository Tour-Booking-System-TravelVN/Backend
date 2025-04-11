package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Integer> {
}
