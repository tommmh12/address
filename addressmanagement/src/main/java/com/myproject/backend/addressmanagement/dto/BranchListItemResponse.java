package com.myproject.backend.addressmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchListItemResponse {
    private Long id;
    private String code;
    private String name;
    private String phone;
    private String status;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String detailAddress;
}