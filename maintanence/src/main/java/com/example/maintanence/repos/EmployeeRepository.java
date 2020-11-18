package com.example.maintanence.repos;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.enums.employee.Crew;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Id> {
    Page<Employee> findByCrew(Crew crew, Pageable pageable);
    Optional<Employee> findBySurname(String surname);
    Optional<Employee> findById(UUID id);
    void deleteById(UUID id);


}
