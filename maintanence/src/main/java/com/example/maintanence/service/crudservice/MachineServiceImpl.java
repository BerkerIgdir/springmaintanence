package com.example.maintanence.service.crudservice;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.dto.MachineDto;
import com.example.maintanence.mapper.MachineMapper;
import com.example.maintanence.repos.MachineRepository;
import com.example.maintanence.service.crudservice.MachineService;
import com.example.maintanence.web.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MachineServiceImpl implements MachineService {

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    MachineMapper machineMapper;

    @Override
    public MachineDto getByName(String name) {

        Machine theMachine = machineRepository.findMachineByName(name).orElseThrow(NotFoundException::new);

        return machineMapper.toMachineDto(theMachine);
    }

    @Override
    public Page<MachineDto> getByStatus(MachineStatus status, Pageable pageable) {

        Page<Machine> machines = machineRepository.findByMachineStatus(status, pageable);

        List<MachineDto> theDto = machines.stream()
                .map(machineMapper::toMachineDto)
                .collect(Collectors.toList());

        return new PageImpl<>(theDto);
    }

    @Override
    public MachineDto getById(UUID id) {

        Machine theMachine = machineRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        return machineMapper.toMachineDto(theMachine);
    }

    @Override
    public Page<MachineDto> getAll() {

        List<MachineDto> theDtos = machineRepository.findAll()
                .stream()
                .map(machineMapper::toMachineDto).collect(Collectors.toList());

        return new PageImpl<>(theDtos);
    }

    @Override
    public Page<MachineDto> getByFactory(Factory factory, Pageable pageable) {

        List<MachineDto> theDtos = machineRepository.findByFactory(factory, pageable)
                .stream()
                .map(machineMapper::toMachineDto)
                .collect(Collectors.toList());

        return new PageImpl<>(theDtos);
    }

    @Override
    public MachineDto save(MachineDto machineDto) {
        Machine savedMachine = machineRepository.save(machineMapper.ToMachine(machineDto));

        return machineMapper.toMachineDto(savedMachine);
    }

    @Override
    public void deleteById(UUID id) {

        Machine machine = machineRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        log.info(machine.getName().concat(" is being deleted.."));
        machineRepository.deleteById(id);

    }
}