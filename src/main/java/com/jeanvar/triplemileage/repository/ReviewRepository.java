package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    boolean existsByPlace(Place place);
}
