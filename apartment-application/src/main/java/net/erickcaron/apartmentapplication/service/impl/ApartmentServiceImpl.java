package net.erickcaron.apartmentapplication.service.impl;

import net.erickcaron.apartmentapplication.exception.ApartmentError;
import net.erickcaron.apartmentapplication.exception.ApartmentException;
import net.erickcaron.apartmentapplication.exception.BedError;
import net.erickcaron.apartmentapplication.exception.BedException;
import net.erickcaron.apartmentapplication.model.Apartment;
import net.erickcaron.apartmentapplication.model.ApartmentStatus;
import net.erickcaron.apartmentapplication.model.Bed;
import net.erickcaron.apartmentapplication.model.BedStatus;
import net.erickcaron.apartmentapplication.repository.ApartmentRepository;
import net.erickcaron.apartmentapplication.service.ApartmentService;
import net.erickcaron.apartmentapplication.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BedService bedService;

    @Override
    public Long create(Apartment apartment) {
        checkIfApartNameExisting(apartment.getName());
        checkApartmentInput(apartment);
        return apartmentRepository.save(apartment).getId();
    }

    private void checkIfApartNameExisting(String name) {
        if (apartmentRepository.existsByName(name)) {
            throw new ApartmentException(ApartmentError.INCORRECT_NAME_INPUT);
        }
    }


    @Override
    public Apartment findById(Long id) {
        return checkIfApartmentExists(id);
    }


    @Override
    public void update(Long id, Apartment apartment) {
        apartmentRepository.save(checkIfApartmentInputIsCorrect(id, apartment));
    }

    private Apartment checkIfApartmentInputIsCorrect(Long id, Apartment apartment) {
        checkIfApartmentExists(id);
        checkApartmentInput(apartment);
        checkIfApartNameExisting(apartment.getName());
        Apartment apartFromDB = checkIfApartmentExists(id);
        apartment.setApartmentStatus(apartFromDB.getApartmentStatus());
        if(!isMaxNbOfPlacesTolerancyRespected(apartment.getMaxNbOfPersons())){
            throw new ApartmentException(ApartmentError.TOLERANCE_NOT_RESPECTED);
        }
        return apartment;

    }

    @Override
    public void deleteById(Long id) {
        Apartment apartmentToDelete = checkIfApartmentExists(id);
        List<Bed> ApartmentToDeleteBedList = apartmentToDelete.getBedList();
        if (ApartmentToDeleteBedList.size() >= 1) {
            for (Bed bed : ApartmentToDeleteBedList) {
                bedService.flagBedAs(bed, BedStatus.FREE);
                }
        }
        apartmentRepository.delete(apartmentToDelete);
    }


    @Override
    public List<Apartment> findAll(ApartmentStatus apartmentStatus) {
        if (apartmentStatus != null) {
            return apartmentRepository.findAllByApartmentStatus(apartmentStatus);
        }
        return apartmentRepository.findAll();
    }

    @Override
    public void addBed(Long id, Long bedId) {
        checkIfBedAlreadyUsed(bedId);
        Apartment apartById = findById(id);
        apartById.getBedList().add(bedService.findById(bedId));
        if(!isMaxNbOfPlacesTolerancyRespected(apartById.getMaxNbOfPersons())){
            throw new ApartmentException(ApartmentError.TOLERANCE_NOT_RESPECTED);
        }
        apartmentRepository.save(apartById);
        bedService.flagBedAs(bedService.findById(bedId), BedStatus.USED);
        generateBedsRelatedDatas(apartById);
        apartmentRepository.save(apartById);

    }

    @Override
    public void deleteBed(Long id, Long bedId) {
        Apartment apartById = findById(id);
        apartById.getBedList().remove(bedService.findById(bedId));
        apartmentRepository.save(apartById);
        bedService.flagBedAs(bedService.findById(bedId), BedStatus.FREE);
        generateBedsRelatedDatas(apartById);
        apartmentRepository.save(apartById);
    }

    @Override
    public void updateBed(Long id, Long bedId, String action) {

        if (action.equals("add")) {
            Apartment apartmentById = findById(id);
            Bed bedToAdd = bedService.findById(bedId);
            Integer nbOfPersons = apartmentById.getNbOfPersons();
            Integer maxNbOfPersonsWithTolerancy = applyTolerancyOnMaxNbOfPersons(apartmentById.getMaxNbOfPersons());

            if(nbOfPersons + bedToAdd.getNbOfPlaces() >= maxNbOfPersonsWithTolerancy){
                throw new ApartmentException(ApartmentError.TOLERANCE_NOT_RESPECTED);
            }
            addBed(id, bedId);
        } else if (action.equals("delete")) {
            deleteBed(id, bedId);
        } else {
            throw new ApartmentException(ApartmentError.INCORRECT_QUERY);
        }
    }

    @Override
    public void updateStatus(Long id, ApartmentStatus apartmentStatus) {
        Apartment apartmentById = findById(id);
        apartmentById.setApartmentStatus(apartmentStatus);
        apartmentRepository.save(apartmentById);
    }

    private Apartment checkIfApartmentExists(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new ApartmentException(ApartmentError.APARTMENT_NOT_FOUND));
    }

    private void checkApartmentInput(Apartment apartment) {
        checkName(apartment.getName());
        checkEquipments(apartment.getEquipments());
    }

    private void checkName(String name) {
        if (name.isEmpty() || name.contains(" ")) {
            throw new ApartmentException(ApartmentError.INCORRECT_NAME_INPUT);
        }
    }

    private void checkEquipments(String equipments) {
        if (equipments.isEmpty()) {
            throw new ApartmentException(ApartmentError.INCORRECT_EQUIPMENTS_INPUT);
        }
    }


    private void generateBedsRelatedDatas(Apartment apartment) {
        apartment.setNbOfBeds(generateNbOfBeds(apartment.getBedList()));
        apartment.setNbOfPersons(generateNbOfPlaces(apartment.getBedList()));

    }

    private Integer generateNbOfBeds(List<Bed> bedList) {
        return bedList.size();
    }

    private Integer generateNbOfPlaces(List<Bed> bedList) {
        Integer nbOfPlaces = 0;
        for (Bed bed : bedList) {
            nbOfPlaces += bed.getNbOfPlaces();
        }
        return nbOfPlaces;
    }

    private boolean isMaxNbOfPlacesTolerancyRespected(Integer nbOfPlaces){
        return nbOfPlaces <= applyTolerancyOnMaxNbOfPersons(nbOfPlaces);
    }

    private void checkIfBedAlreadyUsed(Long bedId) {
        if (bedService.isBedCurrentlyUsed(bedService.findById(bedId))) {
            throw new BedException(BedError.BED_ALREADY_USED);
        }
    }

    private Integer applyTolerancyOnMaxNbOfPersons(Integer maxNbOfPersons) { //TODO universaliser la tolerancy?
        return maxNbOfPersons + 1;
    }

}
