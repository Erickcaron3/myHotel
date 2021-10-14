package net.erickcaron.apartmentapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(name="beds")

public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Positive
    private Integer nbOfPlaces;

    @NotBlank
    private String type;

    @Enumerated(EnumType.STRING)
    private BedStatus bedStatus;

    @PrePersist
    private void PrePersist(){
        bedStatus = BedStatus.CREATED;
    }
}
