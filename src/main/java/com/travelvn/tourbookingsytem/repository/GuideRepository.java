package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Integer> {
}
