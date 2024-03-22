package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.dto.CategoryDTO;
import com.devsuperior.dsCatagog.dto.RoleDTO;
import com.devsuperior.dsCatagog.dto.UserDTO;
import com.devsuperior.dsCatagog.dto.UserInsertDTO;
import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.entities.Role;
import com.devsuperior.dsCatagog.entities.User;
import com.devsuperior.dsCatagog.repositories.CategoryRepository;
import com.devsuperior.dsCatagog.repositories.RoleRepository;
import com.devsuperior.dsCatagog.repositories.UserRepository;
import com.devsuperior.dsCatagog.services.exceptions.DatabaseException;
import com.devsuperior.dsCatagog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
   @Autowired
    private UserRepository userRepository;

   @Autowired
   private RoleRepository roleRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   @Transactional(readOnly= true)
    public Page<UserDTO> findAllPaged(Pageable pageable)  {
        Page<User> list= userRepository.findAll(pageable);
       return list.map(x->new UserDTO(x));
        }
    @Transactional(readOnly= true)
    public UserDTO findByid(Long id) {
        Optional<User> obj= userRepository.findById(id);
        User entity= obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
       User entity = new User();
       copyDtoToEntity(dto, entity);
       entity.setPassword(passwordEncoder.encode(dto.getPassword()));
       entity= userRepository.save(entity);
       return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
       try {
           User entity = userRepository.getReferenceById(id);
           copyDtoToEntity(dto, entity);
           entity = userRepository.save(entity);
           return new UserDTO(entity);
       }
       catch(EntityNotFoundException e){
           throw new ResourceNotFoundException("id not found" + id);
        }
    }

    public void delete(Long id) {
    if(!userRepository.existsById(id)){
        throw new ResourceNotFoundException("Recurso não encontrado");
    }
    try{
        userRepository.deleteById(id);
    }catch (DataIntegrityViolationException e ){
        throw new DatabaseException("Falha de integridade referencial");
    }
}
    private void copyDtoToEntity(UserDTO dto, User entity) {
       entity.setFirstName(dto.getFirstName());
       entity.setLastName(dto.getLastName());
       entity.setEmail(dto.getEmail());

       entity.getRoles().clear();
       for(RoleDTO roleDto : dto.getRoles()){
          Role role = roleRepository.getReferenceById(roleDto.getId());
           entity.getRoles().add(role);
       }
    }}

