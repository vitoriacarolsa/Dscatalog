package com.devsuperior.dsCatagog.repositories;

import com.devsuperior.dsCatagog.entities.Role;
import com.devsuperior.dsCatagog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
