package com.devsuperior.dsCatagog.resources;

import com.devsuperior.dsCatagog.dto.UserDTO;
import com.devsuperior.dsCatagog.dto.UserInsertDTO;
import com.devsuperior.dsCatagog.dto.UserUpdateDTO;
import com.devsuperior.dsCatagog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping (value = "/users")
public class UserResource {
    @Autowired
    private UserService service;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable){

        Page<UserDTO>list= service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
  }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO dto= service.findByid(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> findMe(){
        UserDTO dto= service.findMe();
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto){
       UserDTO newDto= service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value= "/{id}")
    public ResponseEntity<UserDTO> update (@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto){
       UserDTO newDto = service.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value= "/{id}")
    public ResponseEntity<UserDTO> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

