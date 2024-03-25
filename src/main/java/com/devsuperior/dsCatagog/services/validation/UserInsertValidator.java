package com.devsuperior.dsCatagog.services.validation;

import java.util.ArrayList;
import java.util.List;


import jakarta.validation.ConstraintValidatorContext;

import com.devsuperior.dsCatagog.resources.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.devsuperior.dsCatagog.dto.UserInsertDTO;
import com.devsuperior.dsCatagog.entities.User;
import com.devsuperior.dsCatagog.repositories.UserRepository;


public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());
        if(user != null){
            list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}