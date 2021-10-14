package net.erickcaron.apartmentapplication.rest;

import net.erickcaron.apartmentapplication.model.Bed;
import net.erickcaron.apartmentapplication.model.BedStatus;
import net.erickcaron.apartmentapplication.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/beds")
public class BedController {

    @Autowired
    private BedService bedService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long create(@RequestBody Bed bed) {
        return bedService.create(bed);
    }

    @GetMapping
    public List<Bed> findAll(@RequestParam(name = "status", required = false) BedStatus bedStatus) {
        return bedService.findAll(bedStatus);
    }


    @GetMapping("/{id}")
    public Bed findById(@PathVariable("id") @Valid Long id) {
        return bedService.findById(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void update(@PathVariable("id") Long id, @RequestBody Bed bed) {
        bedService.update(id, bed);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {

        bedService.deleteById(id);
    }

}