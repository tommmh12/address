package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.AddressRequest;
import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;
import com.myproject.backend.addressmanagement.entity.Address;
import com.myproject.backend.addressmanagement.entity.Branch;
import com.myproject.backend.addressmanagement.repository.BranchRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    @Test
    void createBranch_shouldCreateBranchWithAddress() {
        BranchCreateRequest request = buildCreateRequest();
        when(branchRepository.existsByCode("BR001")).thenReturn(false);
        when(branchRepository.save(any(Branch.class))).thenAnswer(invocation -> {
            Branch branch = invocation.getArgument(0);
            branch.setId(1L);
            if (branch.getAddress() != null) {
                branch.getAddress().setId(10);
            }
            return branch;
        });

        BranchResponse result = branchService.createBranch(request);

        ArgumentCaptor<Branch> captor = ArgumentCaptor.forClass(Branch.class);
        verify(branchRepository).save(captor.capture());
        Branch savedBranch = captor.getValue();
        assertEquals("BR001", savedBranch.getCode());
        assertEquals("Chi nhanh A", savedBranch.getName());
        assertEquals("ACTIVE", savedBranch.getStatus());
        assertEquals("01", savedBranch.getAddress().getState());
        assertEquals(1L, result.getId());
        assertEquals("BR001", result.getCode());
        assertEquals("01", result.getAddress().getProvinceCode());
    }

    @Test
    void createBranch_shouldThrowWhenCodeAlreadyExists() {
        BranchCreateRequest request = buildCreateRequest();
        when(branchRepository.existsByCode("BR001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> branchService.createBranch(request));
        verify(branchRepository, never()).save(any(Branch.class));
    }

    @Test
    void updateBranch_shouldUpdateExistingBranchAndAddress() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCode("BR001");
        branch.setAddress(new Address());
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        when(branchRepository.save(branch)).thenReturn(branch);

        BranchResponse result = branchService.updateBranch(1L, buildUpdateRequest());

        assertEquals("Chi nhanh moi", branch.getName());
        assertEquals("INACTIVE", branch.getStatus());
        assertEquals("79", branch.getAddress().getState());
        assertEquals("Chi nhanh moi", result.getName());
        assertEquals("79", result.getAddress().getProvinceCode());
    }

    @Test
    void getBranchById_shouldThrowWhenMissing() {
        when(branchRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.getBranchById(99L));
    }

    @Test
    void getAllBranches_shouldMapResults() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCode("BR001");
        branch.setName("Chi nhanh A");
        branch.setPhone("0901234567");
        branch.setStatus("ACTIVE");
        when(branchRepository.findAll()).thenReturn(List.of(branch));

        List<BranchResponse> result = branchService.getAllBranches();

        assertEquals(1, result.size());
        assertEquals("BR001", result.get(0).getCode());
    }

    @Test
    void searchBranches_shouldReturnAllWhenKeywordBlank() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCode("BR001");
        branch.setName("Chi nhanh A");
        when(branchRepository.findAll()).thenReturn(List.of(branch));

        List<BranchResponse> result = branchService.searchBranches("   ");

        assertEquals(1, result.size());
        verify(branchRepository).findAll();
        verify(branchRepository, never()).search(any(String.class));
    }

    @Test
    void searchBranches_shouldUseRepositorySearchWhenKeywordPresent() {
        Branch branch = new Branch();
        branch.setId(1L);
        branch.setCode("BR001");
        branch.setName("Chi nhanh A");
        when(branchRepository.search("A")).thenReturn(List.of(branch));

        List<BranchResponse> result = branchService.searchBranches("A");

        assertEquals(1, result.size());
        assertEquals("BR001", result.get(0).getCode());
    }

    @Test
    void deleteBranch_shouldDeleteExistingBranch() {
        Branch branch = new Branch();
        when(branchRepository.findById(1L)).thenReturn(Optional.of(branch));
        doNothing().when(branchRepository).delete(branch);

        branchService.deleteBranch(1L);

        verify(branchRepository).delete(branch);
    }

    @Test
    void deleteBranch_shouldThrowWhenMissing() {
        when(branchRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> branchService.deleteBranch(1L));
        verify(branchRepository, never()).delete(any(Branch.class));
    }

    private BranchCreateRequest buildCreateRequest() {
        BranchCreateRequest request = new BranchCreateRequest();
        request.setCode("BR001");
        request.setName("Chi nhanh A");
        request.setPhone("0901234567");
        request.setStatus("ACTIVE");
        request.setAddress(buildAddressRequest("01", "001", "00001", "123 Nguyen Trai"));
        return request;
    }

    private BranchUpdateRequest buildUpdateRequest() {
        BranchUpdateRequest request = new BranchUpdateRequest();
        request.setName("Chi nhanh moi");
        request.setPhone("0907654321");
        request.setStatus("INACTIVE");
        request.setAddress(buildAddressRequest("79", "760", "26734", "456 Le Loi"));
        return request;
    }

    private AddressRequest buildAddressRequest(String province, String district, String ward, String detail) {
        AddressRequest request = new AddressRequest();
        request.setProvinceCode(province);
        request.setDistrictCode(district);
        request.setWardCode(ward);
        request.setDetailAddress(detail);
        return request;
    }
}
