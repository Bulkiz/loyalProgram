package com.example.loyalProgram.exceptionHandlingAndValidation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidateRequestBodyList<T> {

    private List<@Valid T> requestBody;
}