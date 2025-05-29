package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}
