package com.example.maintanence.dto;


import com.example.maintanence.domain.BaseEntity;
import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.domain.enums.machines.MachineType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class MachineDto extends BaseEntity {

    @JsonFormat(shape= JsonFormat.Shape.STRING)
    private BigDecimal consumption ;

    @NotNull
    private Long crashCount ;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MachineStatus machineStatus;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MachineType machineType;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Factory factory;

}
