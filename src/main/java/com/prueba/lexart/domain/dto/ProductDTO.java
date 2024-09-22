package com.prueba.lexart.domain.dto;

import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String brand,
        String model,
        List<DataDTO> data
) {

}
