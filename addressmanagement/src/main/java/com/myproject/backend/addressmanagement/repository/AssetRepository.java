package com.myproject.backend.addressmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.backend.addressmanagement.entity.Asset;


import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCode(String code);
    boolean existsByCode(String code);
    List<Asset> findByBranchId(Long branchId);
}
