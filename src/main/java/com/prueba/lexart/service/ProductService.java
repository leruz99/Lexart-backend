package com.prueba.lexart.service;

import com.prueba.lexart.domain.dto.ProductDTO;
import com.prueba.lexart.domain.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    List<ProductResponseDTO> getAllProduct();
}
