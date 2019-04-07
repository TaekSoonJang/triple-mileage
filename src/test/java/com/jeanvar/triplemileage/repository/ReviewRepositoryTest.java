package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    void crud() {
        Review review = new Review();
        review.setContent("Liked!");

        Review saved = reviewRepository.save(review);

        assertThat(reviewRepository.findById(saved.getId())).get().isEqualTo(saved);
    }

    @Test
    void join() {
        User user = new User();
        user.setName("taeksoon");
        user = entityManager.persist(user);

        Place place = new Place();
        place.setName("new place");
        place = entityManager.persist(place);

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setContent("Liked!");

        Review saved = reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        Review found = reviewRepository.findById(saved.getId()).get();

        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getPlace().getId()).isEqualTo(place.getId());
    }
}