package com.example.maintanence.dto;

import com.example.maintanence.domain.BaseEntity;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class EmployeeDto extends BaseEntity {

    private String name;
    private String surname;
    private Crew crew;
    private String malfunctionId;
    @Builder.Default
    private Long fixedCrashes = 0l;
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.AVAILABLE;
    @Builder.Default
    private BigDecimal averageFixedCrashCount = new BigDecimal(0);
}
