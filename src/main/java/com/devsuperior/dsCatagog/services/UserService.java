package com.devsuperior.dsCatagog.services;

import com.devsuperior.dsCatagog.dto.*;
import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.entities.Role;
import com.devsuperior.dsCatagog.entities.User;
import com.devsuperior.dsCatagog.projections.UserDetailsProjection;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
   @Autowired
    private UserRepository userRepository;

   @Autowired
   private RoleRepository roleRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private AuthService authService;

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

    @Transactional(readOnly= true)
    public UserDTO findMe() {
        User entity = authService.authenticated();
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
       User entity = new User();
       copyDtoToEntity(dto, entity);

       entity.getRoles().clear();
       Role role = roleRepository.findByAuthority("ROLE_OPERATOR");
       entity.getRoles().add(role);

       entity.setPassword(passwordEncoder.encode(dto.getPassword()));
       entity= userRepository.save(entity);
       return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
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
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result  = userRepository.searchUserAndRolesByEmail(username);
        if (result.size() == 0){
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection projection : result){
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }
        return user;
    }
}

