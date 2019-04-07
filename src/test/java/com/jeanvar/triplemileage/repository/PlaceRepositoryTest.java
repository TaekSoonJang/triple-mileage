package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlaceRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PlaceRepository placeRepository;

    @Test
    void crud() {
        Place place = new Place();
        place.setName("new place");

        Place saved = placeRepository.save(place);

        assertThat(placeRepository.findById(saved.getId())).get().isEqualTo(saved);
    }
}