package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Review;
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
}