package com.example.maintanence.web.controller;


import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineType;
import com.example.maintanence.dto.MachineDto;
import com.example.maintanence.mapper.MachineMapper;
import com.example.maintanence.service.crudservice.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping("/api/machine")
@RequiredArgsConstructor
public class MachineController {

    @Autowired
    private final MachineService machineService;

    @Autowired
    private final MachineMapper machineMapper;

    @GetMapping
    ResponseEntity<?> getMachineList(){ return new ResponseEntity<>(machineService.getAll(),HttpStatus.OK);}

    @GetMapping("/{id}")
    ResponseEntity<?>  getMachineById(@PathVariable("id") UUID id){

        MachineDto theDto = machineService.getById(id);

        return new ResponseEntity<>(theDto,HttpStatus.OK);
    }

    @GetMapping("/byname/{name}")
    ResponseEntity<?> getMachineByName(@PathVariable("name") String name){

        MachineDto theDto = machineService.getByName(name);

        return new ResponseEntity<>(machineService.getByName(name),HttpStatus.OK);
    }

    @GetMapping("/byfactory/{name}")
    ResponseEntity<?> getMachineByFactory(@PathVariable("name") String name){
        return new ResponseEntity<>(machineService.getByFactory(Enum.valueOf(Factory.class,name),
                PageRequest.of(0,10)),HttpStatus.OK);
    }

    @PostMapping("/register")
    ResponseEntity<?> creatingNewMachine(@RequestBody HashMap <String,String> request){

        Machine machineToSave = Machine.builder().name(request.get("name")).id(null).machineType(Enum.valueOf(MachineType.class,request.get("machineType")))
                .factory(Enum.valueOf(Factory.class,request.get("factory"))).build();


        try{
            MachineDto savedMachineDto = machineService.save(machineMapper.toMachineDto(machineToSave));

            HttpHeaders header = new HttpHeaders();
            header.add("location",savedMachineDto.getId().toString());

            return new ResponseEntity<>(header,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Failure",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{machineId}")
    ResponseEntity<?> deletingMachine(@PathVariable("machineId") UUID id){

            machineService.deleteById(id);
            return new ResponseEntity<>("Deleted successfuly",HttpStatus.OK);
    }

}
