package com.cydeo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ValidationError {

    private String errorField;
    private Object rejectedValue;
    private String reason;

}
