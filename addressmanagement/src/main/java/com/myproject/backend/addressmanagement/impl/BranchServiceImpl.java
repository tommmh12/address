package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.AddressResponse;
import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;
import com.myproject.backend.addressmanagement.entity.Address;
import com.myproject.backend.addressmanagement.entity.Branch;
import com.myproject.backend.addressmanagement.repository.BranchRepository;
import com.myproject.backend.addressmanagement.service.BranchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public BranchResponse createBranch(BranchCreateRequest request) {
        if (branchRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Ma chi nhanh da ton tai");
        }

        Address address = new Address();
        mapAddress(address, request.getAddress());

        Branch branch = new Branch();
        branch.setCode(request.getCode());
        branch.setName(request.getName());
        branch.setPhone(request.getPhone());
        branch.setStatus(request.getStatus());
        branch.setAddress(address);
        branch.setCreatedDate(LocalDateTime.now());
        branch.setModifiedDate(LocalDateTime.now());

        return mapToResponse(branchRepository.save(branch));
    }

    @Override
    @Transactional
    public BranchResponse updateBranch(Long id, BranchUpdateRequest request) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay chi nhanh"));

        branch.setName(request.getName());
        branch.setPhone(request.getPhone());
        branch.setStatus(request.getStatus());
        branch.setModifiedDate(LocalDateTime.now());

        if (branch.getAddress() == null) {
            branch.setAddress(new Address());
        }

        mapAddress(branch.getAddress(), request.getAddress());

        return mapToResponse(branchRepository.save(branch));
    }

    @Override
    @Transactional
    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay chi nhanh"));

        branchRepository.delete(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay chi nhanh"));

        return mapToResponse(branch);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BranchResponse> searchBranches(String keyword) {
        String normalizedKeyword = keyword == null ? "" : keyword.trim();
        if (normalizedKeyword.isEmpty()) {
            return getAllBranches();
        }

        return branchRepository.search(normalizedKeyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void mapAddress(Address address, AddressRequest request) {
        if (request == null) {
            return;
        }

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

    private BranchResponse mapToResponse(Branch branch) {
        AddressResponse addressResponse = null;

        if (branch.getAddress() != null) {
            addressResponse = AddressResponse.builder()
                    .id(branch.getAddress().getId())
                    .provinceCode(branch.getAddress().getState())
                    .districtCode(branch.getAddress().getSubregion())
                    .wardCode(branch.getAddress().getLga())
                    .detailAddress(branch.getAddress().getAddressLine())
                    .build();
        }

        return BranchResponse.builder()
                .id(branch.getId())
                .code(branch.getCode())
                .name(branch.getName())
                .phone(branch.getPhone())
                .status(branch.getStatus())
                .address(addressResponse)
                .build();
    }
}
