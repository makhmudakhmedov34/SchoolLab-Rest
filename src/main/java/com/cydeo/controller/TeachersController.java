package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TeacherDTO;
import com.cydeo.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeachersController {

    private final TeacherService teacherService;

    public TeachersController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDTO> getAllTeachers(){
       List<TeacherDTO> listTeachers = teacherService.findAll();
       return listTeachers;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO){

        TeacherDTO teacher = teacherService.createTeacher(teacherDTO);
        ResponseWrapper responseWrapper = new ResponseWrapper(true,"Teacher is created successfully",
                HttpStatus.CREATED.value(),teacher);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("teacherId",String.valueOf(teacher.getId()))
                .body(responseWrapper);
    }



}
