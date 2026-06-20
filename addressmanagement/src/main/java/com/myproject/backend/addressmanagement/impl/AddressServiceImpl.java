package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.AddressResponse;
import com.myproject.backend.addressmanagement.entity.Address;
import com.myproject.backend.addressmanagement.repository.AddressRepository;
import com.myproject.backend.addressmanagement.service.AddressService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public AddressResponse createAddress(AddressRequest request) {
        Address address = new Address();
        mapRequestToEntity(address, request);
        return mapToResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddress(Integer id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay address"));

        mapRequestToEntity(address, request);
        return mapToResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay address"));
        addressRepository.delete(address);
    }

    @Override
    public AddressResponse getAddressById(Integer id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay address"));
        return mapToResponse(address);
    }

    @Override
    public List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void mapRequestToEntity(Address address, AddressRequest request) {
        address.setState(request.getProvinceCode());
        address.setSubregion(request.getDistrictCode());
        address.setLga(request.getWardCode());
        address.setAddressLine(request.getDetailAddress());
        address.setFullAddress(
                request.getDetailAddress() + ", "
                        + request.getWardCode() + ", "
                        + request.getDistrictCode() + ", "
                        + request.getProvinceCode()
        );
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .provinceCode(address.getState())
                .districtCode(address.getSubregion())
                .wardCode(address.getLga())
                .detailAddress(address.getAddressLine())
                .build();
    }
}
