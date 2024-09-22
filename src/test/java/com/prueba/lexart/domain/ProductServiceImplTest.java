package com.prueba.lexart.domain;

import com.prueba.lexart.domain.dto.DataDTO;
import com.prueba.lexart.domain.dto.ProductDTO;
import com.prueba.lexart.domain.dto.ProductResponseDTO;
import com.prueba.lexart.domain.entities.Data;
import com.prueba.lexart.domain.entities.Product;
import com.prueba.lexart.repository.DataRepository;
import com.prueba.lexart.repository.ProductRepository;
import com.prueba.lexart.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Data data = new Data(1L, 700.0, "azul");
        product = new Product(1L, "Iphone 170", "apple", "2025", List.of(data));
        productDTO = new ProductDTO(1L, "Iphone 170", "apple", "2025", List.of(new DataDTO(1L, 700.0, "azul")));
    }

    @Test
    void getAllProduct_ShouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = productService.getAllProduct();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void saveProduct_ShouldSaveAndReturnProductDTO() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.saveProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.name(), result.name());
        assertEquals(productDTO.brand(), result.brand());

        verify(productRepository, times(1)).save(any(Product.class));
    }
}
