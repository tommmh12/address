package com.myproject.backend.addressmanagement.service;

import java.util.List;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.AddressResponse;
import com.myproject.backend.addressmanagement.entity.Address;

public interface AddressService {

    AddressResponse createAddress(AddressRequest request);

    AddressResponse updateAddress(Integer id, AddressRequest request);

    void deleteAddress(Integer id);

    AddressResponse getAddressById(Integer id);

    List<AddressResponse> getAllAddresses();

}
