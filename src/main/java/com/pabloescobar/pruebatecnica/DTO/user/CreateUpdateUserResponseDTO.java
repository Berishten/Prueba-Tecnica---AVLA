package com.pabloescobar.pruebatecnica.DTO.user;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserResponseDTO {
    private Long id;
    private Date created;
    private Date modified;
    private String token;
}
