package com.jascoffee.jascoffee.repository.user;

import com.jascoffee.jascoffee.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);
}
