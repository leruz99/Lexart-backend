package com.prueba.lexart.domain.mapper;

import com.prueba.lexart.domain.dto.DataDTO;
import com.prueba.lexart.domain.dto.DataResponseDTO;
import com.prueba.lexart.domain.dto.ProductDTO;
import com.prueba.lexart.domain.dto.ProductResponseDTO;
import com.prueba.lexart.domain.entities.Data;
import com.prueba.lexart.domain.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductDTO toProductDTO(Product product){
        List<DataDTO> dataResponse = product.getData().stream()
                .map(data -> new DataDTO(data.getId(),data.getPrice(),data.getColor()))
                .collect(Collectors.toList());
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getModel(),
                dataResponse);
    }
    public static Product toProduct(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.id()); // Si necesitas manejar actualizaciones, ajusta este campo
        product.setName(productDTO.name());
        product.setBrand(productDTO.brand());
        product.setModel(productDTO.model());

        List<Data> dataResponse = productDTO.data().stream()
                .map(dataDTO -> {
                    Data data = new Data(dataDTO.id(), dataDTO.price(), dataDTO.color());
                    data.setProduct(product); // Aquí se establece la relación inversa
                    return data;
                })
                .collect(Collectors.toList());

        product.setData(dataResponse); // Asignación de la lista al producto

        return product;
    }

    public static ProductResponseDTO toProductResponseDTO(Product product){
        List<DataResponseDTO> dataResponse = product.getData().stream()
                .map(data -> new DataResponseDTO(data.getPrice(),data.getColor()))
                .collect(Collectors.toList());


        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setId(product.getId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setBrand(product.getBrand());
        productResponseDTO.setModel(product.getModel());
        productResponseDTO.setData(dataResponse);

        return productResponseDTO;

    }

}
