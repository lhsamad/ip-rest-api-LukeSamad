package com.luke.samad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IpDto {
    private String ip;
    private String status;
}
