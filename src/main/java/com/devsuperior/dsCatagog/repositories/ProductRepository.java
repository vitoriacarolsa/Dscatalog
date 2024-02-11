package com.devsuperior.dsCatagog.repositories;

import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
