package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.repositories.ProductRepository;
import com.devsuperior.dsCatagog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceIT {
   @Autowired
   private ProductService service;

   @Autowired
   private ProductRepository repository;
   private Long existingId;
   private Long nonExistingId;
   private Long countTotalProducts;
    @BeforeEach
    void setUp()throws Exception {
        existingId=1L;
        nonExistingId=1000L;
        countTotalProducts=25L;
    }
    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){
        service.delete(existingId);
        Assertions.assertEquals(countTotalProducts-1, repository.count());
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists(){
       Assertions.assertThrows(ResourceNotFoundException.class, ()->{
           service.delete(nonExistingId);
       });

    }
}
