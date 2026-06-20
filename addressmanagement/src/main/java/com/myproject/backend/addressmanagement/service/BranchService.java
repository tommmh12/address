package com.myproject.backend.addressmanagement.service;

import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;
import java.util.List;

public interface BranchService {
    BranchResponse createBranch(BranchCreateRequest request);

    BranchResponse updateBranch(Long id, BranchUpdateRequest request);

    void deleteBranch(Long id);

    BranchResponse getBranchById(Long id);

    List<BranchResponse> getAllBranches();

    List<BranchResponse> searchBranches(String keyword);
}
