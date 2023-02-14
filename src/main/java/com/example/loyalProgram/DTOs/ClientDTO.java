package com.example.loyalProgram.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
public class ClientDTO {
    private Integer id;
    private String name;
    private Integer merchantId;
}
