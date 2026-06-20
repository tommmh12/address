package com.myproject.backend.addressmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.myproject.backend.addressmanagement.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
