package com.aso.springsecuritystarter.repositories;

import com.aso.springsecuritystarter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
