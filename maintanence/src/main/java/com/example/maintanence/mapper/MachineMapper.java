package com.example.maintanence.mapper;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.dto.MachineDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MachineMapper {
    MachineDto toMachineDto(Machine machine);

    Machine ToMachine(MachineDto machineDto);
}
