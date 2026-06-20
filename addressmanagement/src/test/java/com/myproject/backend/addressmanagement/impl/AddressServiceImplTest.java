package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.AddressResponse;
import com.myproject.backend.addressmanagement.entity.Address;
import com.myproject.backend.addressmanagement.repository.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void createAddress_shouldMapRequestAndReturnResponse() {
        AddressRequest request = buildValidRequest();
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> {
            Address address = invocation.getArgument(0);
            address.setId(99);
            return address;
        });

        AddressResponse result = addressService.createAddress(request);

        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(captor.capture());
        Address savedAddress = captor.getValue();
        assertEquals("01", savedAddress.getState());
        assertEquals("001", savedAddress.getSubregion());
        assertEquals("00001", savedAddress.getLga());
        assertEquals("123 Nguyen Trai", savedAddress.getAddressLine());
        assertEquals("123 Nguyen Trai, 00001, 001, 01", savedAddress.getFullAddress());
        assertEquals(99, result.getId());
        assertEquals("01", result.getProvinceCode());
    }

    @Test
    void updateAddress_shouldUpdateExistingAddress() {
        Address existingAddress = new Address();
        existingAddress.setId(5);
        when(addressRepository.findById(5)).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(existingAddress)).thenReturn(existingAddress);

        AddressResponse result = addressService.updateAddress(5, buildValidRequest());

        assertEquals(5, result.getId());
        assertEquals("01", existingAddress.getState());
        assertEquals("001", existingAddress.getSubregion());
        assertEquals("00001", existingAddress.getLga());
        assertEquals("123 Nguyen Trai", existingAddress.getAddressLine());
    }

    @Test
    void getAddressById_shouldThrowWhenAddressDoesNotExist() {
        when(addressRepository.findById(123)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.getAddressById(123));
    }

    @Test
    void getAllAddresses_shouldMapAllRows() {
        Address first = new Address();
        first.setId(1);
        first.setState("01");
        first.setSubregion("001");
        first.setLga("00001");
        first.setAddressLine("A");

        Address second = new Address();
        second.setId(2);
        second.setState("79");
        second.setSubregion("760");
        second.setLga("26734");
        second.setAddressLine("B");

        when(addressRepository.findAll()).thenReturn(List.of(first, second));

        List<AddressResponse> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
        assertEquals("01", result.get(0).getProvinceCode());
        assertEquals("79", result.get(1).getProvinceCode());
    }

    @Test
    void deleteAddress_shouldDeleteExistingRow() {
        Address address = new Address();
        when(addressRepository.findById(10)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).delete(address);

        addressService.deleteAddress(10);

        verify(addressRepository).delete(address);
    }

    @Test
    void deleteAddress_shouldThrowWhenAddressDoesNotExist() {
        when(addressRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.deleteAddress(10));
        verify(addressRepository, never()).delete(any(Address.class));
    }

    private AddressRequest buildValidRequest() {
        AddressRequest request = new AddressRequest();
        request.setProvinceCode("01");
        request.setDistrictCode("001");
        request.setWardCode("00001");
        request.setDetailAddress("123 Nguyen Trai");
        return request;
    }
}
