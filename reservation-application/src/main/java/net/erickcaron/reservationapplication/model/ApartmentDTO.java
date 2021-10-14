package net.erickcaron.reservationapplication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class ApartmentDTO {

    private Long id;
    private String name;
    private ApartmentDTOStatus apartmentDTOStatus;


}
