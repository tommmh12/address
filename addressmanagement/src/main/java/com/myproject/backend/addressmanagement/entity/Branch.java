package com.myproject.backend.addressmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {

 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã chi nhánh, ví dụ: BR001
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    // Tên chi nhánh
    @Column(name = "name", nullable = false)
    private String name;

    // Số điện thoại chi nhánh
    @Column(name = "phone", length = 20)
    private String phone;

    // ACTIVE, INACTIVE
    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // Một chi nhánh có một địa chỉ
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    // Một chi nhánh có nhiều asset
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asset> assets = new ArrayList<>();
}
