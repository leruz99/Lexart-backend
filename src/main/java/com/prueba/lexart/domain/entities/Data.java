package com.prueba.lexart.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_data_id_generator")
    @SequenceGenerator(name="product_data_id_generator", sequenceName = "product_data_id_seq")
    private Long id;

    private Double price;
    private String color;
    public Data(Long id, Double price, String color) {
        this.id = id;
        this.price = price;
        this.color = color;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;


    public Data(Double price, String color) {
        this.price = price;
        this.color = color;
    }
}
