package com.pabloescobar.pruebatecnica.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUpdateUserResponseDTO {
    private UUID id;
    private Date created;
    private Date modified;
}
