package com.myproject.backend.addressmanagement.controller;

import com.myproject.backend.addressmanagement.dto.BranchCreateRequest;
import com.myproject.backend.addressmanagement.dto.BranchResponse;
import com.myproject.backend.addressmanagement.dto.BranchUpdateRequest;
import com.myproject.backend.addressmanagement.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public BranchResponse create(@Valid @RequestBody BranchCreateRequest request) {
        return branchService.createBranch(request);
    }

    @PutMapping("/{id}")
    public BranchResponse update(@PathVariable Long id, @Valid @RequestBody BranchUpdateRequest request) {
        return branchService.updateBranch(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        branchService.deleteBranch(id);
    }

    @GetMapping("/{id}")
    public BranchResponse getById(@PathVariable Long id) {
        return branchService.getBranchById(id);
    }

    @GetMapping
    public List<BranchResponse> getAll() {
        return branchService.getAllBranches();
    }

    @GetMapping("/search")
    public List<BranchResponse> search(@RequestParam String keyword) {
        return branchService.searchBranches(keyword);
    }
}