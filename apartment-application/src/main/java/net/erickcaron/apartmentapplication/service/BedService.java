package net.erickcaron.apartmentapplication.service;


import net.erickcaron.apartmentapplication.model.Bed;
import net.erickcaron.apartmentapplication.model.BedStatus;

import java.util.List;

public interface BedService {
    Long create(Bed bed);

    List<Bed> findAll(BedStatus bedStatus);

    Bed findById(Long id);

    void update(Long id, Bed bed);

    void deleteById(Long bedId);

    void flagBedAs(Bed bed, BedStatus bedStatus);

    Boolean isBedCurrentlyUsed(Bed bed);

    List<Bed> findByBedStatus(BedStatus bedStatus);
}
