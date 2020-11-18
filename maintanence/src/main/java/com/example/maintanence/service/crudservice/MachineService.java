package com.example.maintanence.service.crudservice;


import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.dto.MachineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MachineService {

    MachineDto getByName(String name);
    Page<MachineDto> getByStatus(MachineStatus status, Pageable pageable);
    MachineDto getById(UUID id);
    Page<MachineDto> getAll();
    Page<MachineDto> getByFactory(Factory factory,Pageable pageable);
    MachineDto save(MachineDto machineDto);
    void deleteById(UUID id);

}
