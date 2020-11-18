package com.example.maintanence.bootstrap;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineType;
import com.example.maintanence.repos.EmployeeRepository;
import com.example.maintanence.repos.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MachineLoader implements CommandLineRunner {

    @Autowired
    private  MachineRepository machineRepo;

    @Autowired
    private  EmployeeRepository employeeRepo;

    @Override
    public void run(String... args) throws Exception {
        loadMachineData();
        loadEmployeeData();
    }

    void loadMachineData(){
        if(machineRepo.count() == 0){
            machineRepo.save(Machine.builder().machineType(MachineType.Dying).name("Boya").factory(Factory.HamIplik).build());

            machineRepo.save(Machine.builder().machineType(MachineType.Packaging).name("Iplik").factory(Factory.HamIplik).build());

            machineRepo.save(Machine.builder().machineType(MachineType.Quality).name("Kumas").factory(Factory.Kumas).build());

            machineRepo.save(Machine.builder().machineType(MachineType.Stenter).name("Ram").factory(Factory.Devanlay).build());
        }
    }
    void loadEmployeeData() throws InterruptedException {
        if (employeeRepo.count() == 0) {
            employeeRepo.save(Employee.builder().crew(Crew.MECHANICAL).name("Berker").surname("Igdir").build());
            employeeRepo.save(Employee.builder().crew(Crew.MECHANICAL).name("Berk").surname("Igdir").build());
            employeeRepo.save(Employee.builder().crew(Crew.ELECTRICAL).name("Berke").surname("Igdir").build());
            employeeRepo.save(Employee.builder().crew(Crew.ELECTRICAL).name("Mert").surname("Igdir").build());

        }

    }
    }
