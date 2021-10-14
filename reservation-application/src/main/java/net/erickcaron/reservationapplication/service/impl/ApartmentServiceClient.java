package net.erickcaron.reservationapplication.service.impl;

import net.erickcaron.reservationapplication.model.ApartmentDTO;
import net.erickcaron.reservationapplication.model.ApartmentDTOStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient
@RequestMapping("/apartments")
public interface ApartmentServiceClient {

    @GetMapping("/{apartId}")
    ApartmentDTO findById(@PathVariable Long apartId);

    @PatchMapping("/status/{id}")
    void updateApartmentStatus(@PathVariable Long id, @RequestBody ApartmentDTOStatus apartmentDTOStatus);

}
