package com.devsuperior.dsCatagog.resources;

import com.devsuperior.dsCatagog.dto.CategoryDTO;
import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping (value = "/categories")
public class CategoryResource {
    @Autowired
    private CategoryService service;
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
    List<CategoryDTO>list= service.findAll();
      return ResponseEntity.ok().body(list);
  }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO dto= service.findByid(id);
        return ResponseEntity.ok().body(dto);
    }
}

