package com.myproject.backend.addressmanagement.repository;

import com.myproject.backend.addressmanagement.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByCode(String code);

    boolean existsByCode(String code);

    List<Branch> findByNameContainingIgnoreCase(String keyword);

    @Query("""
            select b from Branch b
            where lower(b.code) like lower(concat('%', :keyword, '%'))
               or lower(b.name) like lower(concat('%', :keyword, '%'))
               or lower(coalesce(b.phone, '')) like lower(concat('%', :keyword, '%'))
            """)
    List<Branch> search(@Param("keyword") String keyword);
}
