package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
}
