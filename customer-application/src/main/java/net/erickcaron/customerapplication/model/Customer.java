package net.erickcaron.customerapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor

@Entity(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    //TODO Email

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;


    @PrePersist
    private void PrePersist() {
        customerStatus = CustomerStatus.NEW;
    }
}
