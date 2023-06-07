package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ParentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllParents(){

        return ResponseEntity.status(HttpStatus.ACCEPTED).header("Parent","Returned")
                .body(new ResponseWrapper(true,"Students are successfully retrieved",HttpStatus.ACCEPTED.value(),parentService.findAll()));
    }

}
