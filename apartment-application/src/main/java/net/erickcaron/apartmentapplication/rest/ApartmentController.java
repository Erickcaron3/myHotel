package net.erickcaron.apartmentapplication.rest;

import net.erickcaron.apartmentapplication.model.Apartment;
import net.erickcaron.apartmentapplication.model.ApartmentStatus;
import net.erickcaron.apartmentapplication.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/apartments")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @GetMapping
    public List<Apartment> findAll(@RequestParam (required = false) ApartmentStatus apartmentStatus) {
        return apartmentService.findAll(apartmentStatus);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long create(@RequestBody Apartment apartment) {
        return apartmentService.create(apartment);
    }

    @GetMapping("/{id}")
    public Apartment findById(@PathVariable("id") Long id) {
        return apartmentService.findById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Apartment apartment) {
        apartmentService.update(id, apartment);
    }

    @PatchMapping("/{id}/beds/{bedId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateBed(@PathVariable("id") Long id, @PathVariable("bedId") Long bedId, @RequestParam(name = "action") String action) {
        apartmentService.updateBed(id, bedId, action);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        apartmentService.deleteById(id);
    }

    @PatchMapping("/status/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void updateStatus(@PathVariable("id") Long id, @RequestBody ApartmentStatus apartmentStatus){
        apartmentService.updateStatus(id, apartmentStatus);
    }

}
