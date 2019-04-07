package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.PointsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PointsHistoryRepository extends JpaRepository<PointsHistory, UUID> {
}
