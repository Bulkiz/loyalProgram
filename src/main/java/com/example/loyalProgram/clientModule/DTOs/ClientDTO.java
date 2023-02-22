package com.example.loyalProgram.clientModule.DTOs;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Integer id;
    private String name;
    private LocalDate birthday;
    private Integer merchantId;
    private Integer tierId;
}
