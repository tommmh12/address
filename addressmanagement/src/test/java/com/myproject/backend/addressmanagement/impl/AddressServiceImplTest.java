package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressServiceImplTest {

    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressServiceImpl();
    }

    @Test
    void createAddress_shouldMapAdministrativeCodesIntoAddressEntity() {
        AddressRequest request = buildValidRequest();

        Address result = addressService.createAddress(request);

        assertEquals("01", result.getState());
        assertEquals("001", result.getSubregion());
        assertEquals("00001", result.getLga());
        assertEquals("123 Nguyen Trai", result.getAddressLine());
        assertEquals("123 Nguyen Trai, 00001, 001, 01", result.getFullAddress());
    }

    @Test
    void updateAddress_shouldMutateExistingAddressAndReturnSameInstance() {
        Address existingAddress = new Address();
        existingAddress.setAddressLine("old");
        existingAddress.setState("old");
        existingAddress.setSubregion("old");
        existingAddress.setLga("old");

        AddressRequest request = buildValidRequest();

        Address result = addressService.updateAddress(existingAddress, request);

        assertSame(existingAddress, result);
        assertEquals("01", existingAddress.getState());
        assertEquals("001", existingAddress.getSubregion());
        assertEquals("00001", existingAddress.getLga());
        assertEquals("123 Nguyen Trai", existingAddress.getAddressLine());
        assertEquals("123 Nguyen Trai, 00001, 001, 01", existingAddress.getFullAddress());
    }

    @Test
    void validateAddress_shouldThrowWhenProvinceCodeIsBlank() {
        AddressRequest request = buildValidRequest();
        request.setProvinceCode(" ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addressService.validateAddress(request)
        );

        assertEquals("Province code khong duoc de trong", exception.getMessage());
    }

    @Test
    void buildFullAddress_shouldReturnCommaSeparatedAdministrativeAddress() {
        AddressRequest request = buildValidRequest();

        String result = addressService.buildFullAddress(request);

        assertEquals("123 Nguyen Trai, 00001, 001, 01", result);
    }

    @Test
    void createAddress_shouldThrowWhenRequestIsNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addressService.createAddress(null)
        );

        assertEquals("Dia chi khong duoc de trong", exception.getMessage());
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
