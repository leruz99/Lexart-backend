package com.prueba.lexart.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
        Long id;
        String name;
        String brand;
        String model;
        List<DataResponseDTO> data;

}
