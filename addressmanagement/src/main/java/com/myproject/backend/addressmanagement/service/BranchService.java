package com.myproject.backend.addressmanagement.service;

import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;

public class BranchService {
    BranchResponse createBranch(BranchCreateRequest request);

    BranchResponse updateBranch(Integer branchId, BranchUpdateRequest request);

    void deleteBranch(Integer branchId);

    BranchResponse getBranchById(Integer branchId);

    List<BranchResponse> searchBranches(String keyword);
}   
