package com.jeanvar.triplemileage.repository;

import com.jeanvar.triplemileage.domain.PointsHistory;
import com.jeanvar.triplemileage.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PointsHistoryRepositoryTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PointsHistoryRepository pointsHistoryRepository;

    @Test
    void crud() {
        User user = new User();
        user = testEntityManager.persist(user);

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPointsChanged(3);
        pointsHistory.setReason("No Reason.");

        pointsHistoryRepository.saveAndFlush(pointsHistory);
        testEntityManager.clear();

        User foundUser = testEntityManager.find(User.class, user.getId());
        PointsHistory found = pointsHistoryRepository.findById(pointsHistory.getId()).get();

        assertThat(found.getUser()).isEqualTo(foundUser);
        assertThat(found.getPointsChanged()).isEqualTo(3);
        assertThat(found.getReason()).isEqualTo("No Reason.");
    }
}