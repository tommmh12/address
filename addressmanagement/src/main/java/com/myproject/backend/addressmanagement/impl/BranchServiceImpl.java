package com.myproject.backend.addressmanagement.impl;

import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;
import com.myproject.backend.addressmanagement.service.BranchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Override
    public BranchResponse createBranch(BranchCreateRequest request) {
        throw new UnsupportedOperationException("BranchService chua duoc hoan thien");
    }

    @Override
    public BranchResponse updateBranch(Long branchId, BranchUpdateRequest request) {
        throw new UnsupportedOperationException("BranchService chua duoc hoan thien");
    }

    @Override
    public void deleteBranch(Long branchId) {
        throw new UnsupportedOperationException("BranchService chua duoc hoan thien");
    }

    @Override
    public BranchResponse getBranchById(Long branchId) {
        throw new UnsupportedOperationException("BranchService chua duoc hoan thien");
    }

    @Override
    public List<BranchResponse> searchBranches(String keyword) {
        throw new UnsupportedOperationException("BranchService chua duoc hoan thien");
    }
}
