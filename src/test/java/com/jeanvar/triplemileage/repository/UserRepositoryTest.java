package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void crud() {
        User user = new User();
        user.setName("taeksoon");

        User found = userRepository.save(user);

        assertThat(userRepository.findById(found.getId())).get().isEqualTo(found);
    }
}