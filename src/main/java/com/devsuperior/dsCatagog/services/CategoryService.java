package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.dto.CategoryDTO;
import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.repositories.CategoryRepository;
import com.devsuperior.dsCatagog.services.exceptions.DatabaseException;
import com.devsuperior.dsCatagog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

       return list.stream().map(x->new CategoryDTO(x)).toList();
        }
    @Transactional(readOnly= true)
    public CategoryDTO findByid(Long id) {
        Optional<Category> obj= repository.findById(id);
        Category entity= obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
       Category entity = new Category();
       entity.setName(dto.getName());
       entity= repository.save(entity);
       return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
       try {
           Category entity = repository.getReferenceById(id);
           entity.setName(dto.getName());
           entity = repository.save(entity);
           return new CategoryDTO(entity);
       }
       catch(EntityNotFoundException e){
           throw new ResourceNotFoundException("id not found" + id);
        }
    }

    public void delete(Long id) {
    if(!repository.existsById(id)){
        throw new ResourceNotFoundException("Recurso não encontrado");
    }
    try{
        repository.deleteById(id);
    }catch (DataIntegrityViolationException e ){
        throw new DatabaseException("Falha de integridade referencial");
    }
}}

