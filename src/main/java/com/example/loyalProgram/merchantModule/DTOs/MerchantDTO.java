package com.example.loyalProgram.merchantModule.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class MerchantDTO {
    private Integer id;
    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters!")
    private String name;

}
