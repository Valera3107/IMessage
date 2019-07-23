package com.ua.repository;

import com.ua.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Long>{
  Optional<User> findByUsername(String username);

  User findByActivationCode(String code);
}
