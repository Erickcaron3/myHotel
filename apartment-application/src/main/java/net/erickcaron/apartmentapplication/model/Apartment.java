package net.erickcaron.apartmentapplication.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "apartments")

public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @NotBlank
    private String equipments;

    private Integer nbOfBeds;

    private Integer nbOfPersons;

    @Positive
    private Integer maxNbOfPersons;

    @OneToMany
    private List<Bed> bedList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ApartmentStatus apartmentStatus;

    @PrePersist
    private void PrePersist() {
        apartmentStatus = ApartmentStatus.FREE;
        nbOfBeds = 0;
        nbOfPersons = 0;
    }

    public Integer applyTolerancyOnMaxNbOfPersons(Integer maxNbOfPersons){
        return maxNbOfPersons+1;
    }

}


