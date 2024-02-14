package com.devsuperior.dsCatagog.repositories;

import com.devsuperior.dsCatagog.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        long exintingId = 1L;
        productRepository.deleteById(exintingId);
       Optional<Product> result= productRepository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());
    }
}
