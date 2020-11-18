package com.example.maintanence.repos;


import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.domain.Machine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MachineRepository extends JpaRepository<Machine,Long> {

    Optional<Machine> findMachineByName(String machineName);

    Page<Machine> findByMachineStatus(MachineStatus status,Pageable pageable);

    Optional<Machine> findById(UUID id);

    Page<Machine> findByFactory(Factory factory,Pageable pageable);

    void deleteById(UUID id);

    Optional<Machine> deleteByName(String Name);
}
