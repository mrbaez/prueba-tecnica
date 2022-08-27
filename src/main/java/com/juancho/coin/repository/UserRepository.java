package com.juancho.coin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juancho.coin.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUserName(String userName);

   boolean existsByUserName(String userName);

   boolean existsByUserNameAndIdNot(String userName, Long id);
}
