package com.chenu.patel.hospitalManagement.repository;

import com.chenu.patel.hospitalManagement.entity.User;
import com.chenu.patel.hospitalManagement.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User>findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
    Optional<User> findByUsername(String username);
}