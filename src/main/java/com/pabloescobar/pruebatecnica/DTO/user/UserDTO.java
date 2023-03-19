package com.pabloescobar.pruebatecnica.DTO.user;

import java.util.List;

import com.pabloescobar.pruebatecnica.DTO.phone.PhoneDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String name;

    private String email;

    private String password;

    private List<PhoneDTO> phones;
}
