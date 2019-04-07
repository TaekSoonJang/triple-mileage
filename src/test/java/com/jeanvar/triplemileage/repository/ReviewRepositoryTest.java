package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Photo;
import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

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

        Photo p1 = new Photo();
        p1.setUrl("http://path/to/p1");
        p1 = entityManager.persist(p1);

        Photo p2 = new Photo();
        p2.setUrl("http://path/to/p2");
        p2 = entityManager.persist(p2);

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setContent("Liked!");
        review.attachPhotos(Arrays.asList(p1, p2));

        Review saved = reviewRepository.save(review);
        entityManager.flush();
        entityManager.clear();

        Review found = reviewRepository.findById(saved.getId()).get();

        assertThat(found.getUser().getId()).isEqualTo(user.getId());
        assertThat(found.getPlace().getId()).isEqualTo(place.getId());
        assertThat(found.getAttachedPhotos()).hasSize(2);
        assertThat(found.getAttachedPhotos().get(0).getId()).isEqualTo(p1.getId());
        assertThat(found.getAttachedPhotos().get(1).getId()).isEqualTo(p2.getId());
    }

    @Test
    void user_place_unique() {
        User user = new User();
        user = entityManager.persist(user);

        Place place = new Place();
        place = entityManager.persist(place);

        Review r1 = new Review();
        r1.setUser(user);
        r1.setPlace(place);
        r1 = reviewRepository.save(r1);

        Review r2 = new Review();
        r2.setUser(user);
        r2.setPlace(place);
        r2 = reviewRepository.save(r2);

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> entityManager.flush());
    }

    @Test
    void existsByPlace() {
        Place place = new Place();

        assertThat(reviewRepository.existsByPlace(place)).isFalse();
    }
}