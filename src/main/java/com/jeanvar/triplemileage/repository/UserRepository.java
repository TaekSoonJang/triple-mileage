package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
