package com.myproject.backend.addressmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BranchCreateRequest {

    @NotBlank(message = "Ma chi nhanh khong duoc de trong")
    @Size(max = 50, message = "Ma chi nhanh toi da 50 ky tu")
    private String code;

    @NotBlank(message = "Ten chi nhanh khong duoc de trong")
    @Size(max = 255, message = "Ten chi nhanh toi da 255 ky tu")
    private String name;

    @Pattern(regexp = "^(0|\\+84)[0-9]{9,10}$", message = "So dien thoai khong hop le")
    private String phone;

    @NotBlank(message = "Trang thai khong duoc de trong")
    private String status;

    @Valid
    private AddressRequest address;
}
