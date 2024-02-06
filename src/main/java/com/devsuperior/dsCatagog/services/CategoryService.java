package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
   @Autowired
    private CategoryRepository repository;
   @Transactional(readOnly= true)
    public List<Category> findAll()  {
        return repository.findAll();
    }
}
