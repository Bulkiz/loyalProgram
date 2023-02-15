package com.example.loyalProgram.ClientModule.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Integer id;
    private String name;
    private Integer merchantId;
    private Integer tierId;
}
