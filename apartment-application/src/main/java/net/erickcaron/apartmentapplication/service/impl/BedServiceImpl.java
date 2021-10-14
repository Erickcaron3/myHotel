package net.erickcaron.apartmentapplication.service.impl;

import net.erickcaron.apartmentapplication.exception.BedError;
import net.erickcaron.apartmentapplication.exception.BedException;
import net.erickcaron.apartmentapplication.model.Bed;
import net.erickcaron.apartmentapplication.model.BedStatus;
import net.erickcaron.apartmentapplication.repository.BedRepository;
import net.erickcaron.apartmentapplication.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedRepository bedRepository;

    @Override
    public Long create(Bed bed) {
        checkBedInput(bed);
        return bedRepository.save(bed).getId();
    }

    @Override
    public List<Bed> findAll(BedStatus bedStatus) {
        if(bedStatus != null){
            return bedRepository.findAllByBedStatus(bedStatus);
        }

        return bedRepository.findAll();
    }

    @Override
    public Bed findById(Long id) {
        return checkIfBedExisting(id);
    }

    @Override
    public void update(Long id, Bed bed) {
        Bed bedFromDB = checkIfBedExisting(id);
        bed.setId(id);
        bed.setBedStatus(bedFromDB.getBedStatus());
        bedRepository.save(bed);
    }

    @Override
    public void deleteById(Long id) {
        Bed bedToDelete = checkIfBedExisting(id);
        if(isBedCurrentlyUsed(bedToDelete)){
            throw new BedException(BedError.BED_USED_NOT_DELETABLE);
        }
        bedRepository.delete(bedToDelete);
    }

    @Override
    public void flagBedAs(Bed bed, BedStatus bedStatus) {
        bed.setBedStatus(bedStatus);
        bedRepository.save(bed);
    }

    @Override
    public Boolean isBedCurrentlyUsed(Bed bed) {
        return BedStatus.USED.equals(bed.getBedStatus());
    }

    @Override
    public List<Bed> findByBedStatus(BedStatus bedStatus) {
        return bedRepository.findAllByBedStatus(bedStatus);
    }

    private void checkBedInput(Bed bed) {
        checkBedType(bed.getType());
        checkBedNbOfPlaces(bed.getNbOfPlaces());

    }

    private void checkBedType(String bedType) {
        if (bedType.isEmpty() || bedType.contains(" ")) {
            throw new BedException(BedError.INCORRECT_NAME_INPUTTED);
        }
    }

    private void checkBedNbOfPlaces(Integer nbOfPlaces) {
        if (nbOfPlaces <= 0) {
            throw new BedException(BedError.INCORRECT_NB_OF_PLACES_INPUTTED);
        }
    }

    private Bed checkIfBedExisting(Long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new BedException(BedError.BED_NOT_FOUND));
    }

    private BedStatus parseBedStatus(String status) {
        BedStatus bedStatus = null;

        switch (status) {
            case "created":
                bedStatus = BedStatus.CREATED;
                break;
            case "free":
                bedStatus = BedStatus.FREE;
                break;
            case "used":
                bedStatus = BedStatus.USED;
                break;
            case "unavailable":
                bedStatus = BedStatus.UNAVAILABLE;
                break;
        }
        return bedStatus;
    }

}
