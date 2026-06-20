package com.myproject.backend.addressmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.backend.addressmanagement.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    Optional<Branch> findByCode(String code);

    boolean existsByCode(String code);
}
