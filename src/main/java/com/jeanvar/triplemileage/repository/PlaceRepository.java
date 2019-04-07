package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
}
