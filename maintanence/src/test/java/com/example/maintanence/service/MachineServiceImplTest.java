package com.example.maintanence.service;

import com.example.maintanence.dto.MachineDto;
import com.example.maintanence.mapper.MachineMapper;
import com.example.maintanence.repos.MachineRepository;
import com.example.maintanence.service.crudservice.MachineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MachineServiceImplTest {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private MachineMapper machineMapper;

    @Autowired
    MachineServiceImpl service;

    @BeforeEach
    void SetUp(){

    }
    @Test
    void getByName() {

        MachineDto theDto = service.getByName("Boya");

        assertEquals("Boya",theDto);
        assertNull(theDto.getId());

        System.out.println(theDto.getCreationTime());

    }
}