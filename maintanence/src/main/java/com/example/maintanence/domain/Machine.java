package com.example.maintanence.domain;



import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.domain.enums.machines.MachineType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Machine extends BaseEntity {

    @Builder.Default
    private BigDecimal consumption = new BigDecimal(0);
    @Builder.Default
    private Long crashCount = 0L;
    @Column(length = 50,columnDefinition = "varchar(50)",unique = true)
    private String name;

//    @Builder
//    public Machine(MachineType machineType, UUID theId,Long version, Timestamp creationTime, String name, Timestamp updateTime,
//                   MachineStatus machineStatus,Long crashCount,BigDecimal consumption)
//    {
//        super(theId,version,creationTime,name,updateTime);
//        this.machineType = machineType;
//        this.machineStatus = machineStatus;
//        this.crashCount = crashCount;
//        this.consumption = consumption;
//    }

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MachineStatus machineStatus = MachineStatus.OFF;
    @Enumerated(EnumType.STRING)
    private MachineType machineType;
    @Enumerated(EnumType.STRING)
    private Factory factory;

}
