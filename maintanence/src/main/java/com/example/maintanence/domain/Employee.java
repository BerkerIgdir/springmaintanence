package com.example.maintanence.domain;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.domain.enums.employee.EmployeeEvent;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"name", "surname"})
)
public class Employee extends BaseEntity {

    @NotNull
    @Column(length = 50,columnDefinition = "varchar(50)")
    private String name;

    @NotNull
    @Column(length = 25,columnDefinition = "varchar(25)")
    private String surname;
    @NotNull
    private Crew crew;
    @Builder.Default
    private Long fixedCrashes = 0L;
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.AVAILABLE;
//    @Builder
//    public Employee(Crew theCrew, UUID theId,Long version, Timestamp theCreationTime, String theName, Timestamp theUpdateTime,String theSurname)
//    {
//        super(theId,version,theCreationTime,theName,theUpdateTime);
//        crew = theCrew;
//        surname = theSurname;
//    }

}
