package com.myproject.backend.addressmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private Integer id;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String detailAddress;
}