package com.luke.samad.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class CidrDto {
    private String cidr;
    private Set<IpDto> ips = new HashSet<>();
}
