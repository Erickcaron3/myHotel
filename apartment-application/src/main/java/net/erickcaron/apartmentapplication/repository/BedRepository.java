package net.erickcaron.apartmentapplication.repository;

import net.erickcaron.apartmentapplication.model.Apartment;
import net.erickcaron.apartmentapplication.model.Bed;
import net.erickcaron.apartmentapplication.model.BedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findAllByBedStatus(BedStatus bedStatus);
}
