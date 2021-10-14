package net.erickcaron.apartmentapplication.repository;

import net.erickcaron.apartmentapplication.model.Apartment;
import net.erickcaron.apartmentapplication.model.ApartmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Boolean existsByName(String name);
    List<Apartment> findAllByApartmentStatus(ApartmentStatus apartmentStatus);
}
