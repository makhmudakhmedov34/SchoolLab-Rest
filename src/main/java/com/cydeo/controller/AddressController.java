package com.cydeo.controller;

import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getAddress(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(new ResponseWrapper("Address" +id+"is successfully retrieved",addressService.findById(id)));
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddress(@PathVariable("id") Long id,@RequestBody AddressDTO addressDTO) throws Exception {
        addressDTO.setId(id);
         AddressDTO updateAddress  = addressService.update(addressDTO);
         return updateAddress;
    }
}
