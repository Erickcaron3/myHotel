package net.erickcaron.apartmentapplication.service;

import net.erickcaron.apartmentapplication.model.Apartment;
import net.erickcaron.apartmentapplication.model.ApartmentStatus;

import java.util.List;

public interface ApartmentService {
    Long create(Apartment apartment);

    Apartment findById(Long id);

    void update(Long id, Apartment apartment);

    void deleteById(Long id);

    List<Apartment> findAll(ApartmentStatus apartmentStatus);

    void addBed(Long id, Long bedId);

    void deleteBed(Long id, Long bedId);

    void updateBed(Long id, Long bedId, String action);

    void updateStatus(Long id, ApartmentStatus apartmentStatus);
}
