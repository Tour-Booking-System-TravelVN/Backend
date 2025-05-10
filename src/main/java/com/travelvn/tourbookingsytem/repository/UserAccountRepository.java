package com.travelvn.tourbookingsytem.repository;

import com.travelvn.tourbookingsytem.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    boolean existsByCId(Integer cId);
    boolean existsByAdministratorId(Integer administratorId);
    boolean existsByTourGuideId(Integer tourGuideId);
    boolean existsByTourOperatorId(Integer tourOperatorId);

    boolean existsByCIdAndUsernameNot(Integer cId, String username);
    boolean existsByAdministratorIdAndUsernameNot(Integer administratorId, String username);
    boolean existsByTourGuideIdAndUsernameNot(Integer tourGuideId, String username);
    boolean existsByTourOperatorIdAndUsernameNot(Integer tourOperatorId, String username);
}