package com.devsuperior.dsCatagog.resources;

import com.devsuperior.dsCatagog.dto.EmailDTO;
import com.devsuperior.dsCatagog.dto.UserDTO;
import com.devsuperior.dsCatagog.dto.UserInsertDTO;
import com.devsuperior.dsCatagog.dto.UserUpdateDTO;
import com.devsuperior.dsCatagog.services.AuthService;
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
@RequestMapping (value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body){

        authService.createRecoverToken(body);
        return ResponseEntity.noContent().build();
  }
}

