package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
