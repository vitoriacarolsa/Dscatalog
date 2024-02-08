package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.dto.CategoryDTO;
import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.repositories.CategoryRepository;
import com.devsuperior.dsCatagog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
   @Autowired
    private CategoryRepository repository;
   @Transactional(readOnly= true)
    public List<CategoryDTO> findAll()  {
        List<Category> list= repository.findAll();

        return list.stream().map(x->new CategoryDTO(x)).collect(Collectors.toList());
        }
    @Transactional(readOnly= true)
    public CategoryDTO findByid(Long id) {
        Optional<Category> obj= repository.findById(id);
        Category entity= obj.orElseThrow(()-> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }
}

