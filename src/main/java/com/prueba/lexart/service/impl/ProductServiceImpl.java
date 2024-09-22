package com.prueba.lexart.service.impl;

import com.prueba.lexart.domain.dto.ProductDTO;
import com.prueba.lexart.domain.dto.ProductResponseDTO;
import com.prueba.lexart.domain.entities.Product;
import com.prueba.lexart.domain.mapper.ProductMapper;
import com.prueba.lexart.repository.DataRepository;
import com.prueba.lexart.repository.ProductRepository;
import com.prueba.lexart.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DataRepository dataRepository;

    public ProductServiceImpl(ProductRepository productRepository, DataRepository dataRepository) {
        this.productRepository = productRepository;
        this.dataRepository = dataRepository;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toProduct(productDTO);
        return ProductMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {

        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public List<ProductResponseDTO> getAllProduct() {

        return productRepository.findAll().stream()
                .map(ProductMapper::toProductResponseDTO)
                .collect(Collectors.toList());
    }
}
