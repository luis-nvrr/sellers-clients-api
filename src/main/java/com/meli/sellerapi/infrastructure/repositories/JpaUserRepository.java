package com.meli.sellerapi.infrastructure.repositories;

import com.meli.sellerapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface JpaUserRepository extends JpaRepository<User, Long> {
}
