package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final StudentService studentService;

    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllStudents(){
        return ResponseEntity.ok(new ResponseWrapper("Students are successfully retrieved",studentService.findAll()));
    }
}
