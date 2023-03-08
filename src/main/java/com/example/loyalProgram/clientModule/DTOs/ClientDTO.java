package com.example.loyalProgram.clientModule.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClientDTO {
    private Integer id;
    @NotNull(message = "Name cannot be empty!")
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters!")
    private String name;
    @NotNull(message = "You should provide your birthday date!")
    private LocalDate birthday;
    @NotNull(message = "Missing tier for the client")
    private Integer tierId;
}
