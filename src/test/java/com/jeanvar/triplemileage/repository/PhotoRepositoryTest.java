package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.Photo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PhotoRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PhotoRepository photoRepository;

    @Test
    void crud() {
        Photo photo = new Photo();
        photo.setUrl("http://new.photo.com");

        Photo saved = photoRepository.save(photo);

        assertThat(photoRepository.findById(saved.getId())).get().isEqualTo(saved);
    }
}