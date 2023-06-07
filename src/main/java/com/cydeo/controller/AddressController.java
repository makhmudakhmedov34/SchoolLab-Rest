package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
