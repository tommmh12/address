package com.myproject.backend.addressmanagement.controller;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.AddressResponse;
import com.myproject.backend.addressmanagement.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressResponse create(@Valid @RequestBody AddressRequest request) {
        return addressService.createAddress(request);
    }

    @PutMapping("/{id}")
    public AddressResponse update(@PathVariable Integer id, @Valid @RequestBody AddressRequest request) {
        return addressService.updateAddress(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        addressService.deleteAddress(id);
    }

    @GetMapping("/{id}")
    public AddressResponse getById(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }

    @GetMapping
    public List<AddressResponse> getAll() {
        return addressService.getAllAddresses();
    }
}
