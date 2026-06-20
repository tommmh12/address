package com.myproject.backend.addressmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank(message = "Province code khong duoc de trong")
    private String provinceCode;

    @NotBlank(message = "District code khong duoc de trong")
    private String districtCode;

    @NotBlank(message = "Ward code khong duoc de trong")
    private String wardCode;

    @NotBlank(message = "Dia chi chi tiet khong duoc de trong")
    @Size(max = 500, message = "Dia chi chi tiet toi da 500 ky tu")
    private String detailAddress;
}