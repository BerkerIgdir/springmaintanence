package com.example.maintanence.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MalfunctionDto {
    private String id;
    private String malfunctionType;
    private String status;
    @Builder.Default
    private Set<UUID> employeeId = new HashSet<>();
}
