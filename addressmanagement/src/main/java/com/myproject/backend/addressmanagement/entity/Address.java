package com.myproject.backend.addressmanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "createdby_username", length = 99)
    private String createdByUsername;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "full_address", length = 512)
    private String fullAddress;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modifiedby_username", length = 99)
    private String modifiedByUsername;

    @Column(name = "postcode")
    private Integer postcode;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "country", length = 16)
    private String country;

    @Column(name = "site_id", length = 99)
    private String siteId;

    @Column(name = "tool_id", length = 99)
    private String toolId;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "lga")
    private String lga;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "subregion", length = 64)
    private String subregion;
}
