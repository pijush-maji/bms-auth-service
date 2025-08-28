package com.bms.auth.repository;

import com.bms.auth.model.BmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BmsUserRepo extends JpaRepository<BmsUser, Long> {

    Optional<BmsUser> findByEmail(String email);
}
