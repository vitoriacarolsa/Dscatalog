package com.devsuperior.dsCatagog.repositories;

import com.devsuperior.dsCatagog.entities.Category;
import com.devsuperior.dsCatagog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
