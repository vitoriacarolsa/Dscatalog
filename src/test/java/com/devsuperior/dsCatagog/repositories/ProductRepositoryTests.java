package com.devsuperior.dsCatagog.repositories;

import com.devsuperior.dsCatagog.entities.Product;
import com.devsuperior.dsCatagog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
    @DataJpaTest
    public class ProductRepositoryTests {

        @Autowired
        private ProductRepository productRepository;

        private long exintingId;
        private long countTotalProducts;
        private long notExintingId;
        @BeforeEach
        void setUp()throws Exception{
            exintingId=1L;
            countTotalProducts=25L;
            notExintingId=29L;

        }

        @Test
        public void deleteShouldDeleteObjectWhenIdExists(){
            productRepository.deleteById(exintingId);
            Optional<Product> result= productRepository.findById(exintingId);
            Assertions.assertFalse(result.isPresent());
        }

        @Test
        public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
            Product product = Factory.createProduct();
            product.setId(null);

            product = productRepository.save(product);
            Assertions.assertNotNull(product.getId());
            Assertions.assertEquals(countTotalProducts+1, product.getId());
        }

        @Test
        public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){
         Optional<Product> result= productRepository.findById(exintingId);
         Assertions.assertTrue(result.isPresent());
        }

        @Test
        public void findByIdShoulReturnEmptyOptionalWhenIdDoesNotExists(){
            Optional<Product> result= productRepository.findById(notExintingId);
            Assertions.assertTrue(result.isEmpty());
        }

}
