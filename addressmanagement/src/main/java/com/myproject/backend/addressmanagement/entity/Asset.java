package com.myproject.backend.addressmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã tài sản, ví dụ: AS001
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    // Tên tài sản, ví dụ: Máy in, laptop, camera
    @Column(name = "name", nullable = false)
    private String name;

    // Loại tài sản
    @Column(name = "type", length = 100)
    private String type;

    // ACTIVE, INACTIVE, BROKEN, MAINTENANCE
    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    // Nhiều asset thuộc một branch
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
