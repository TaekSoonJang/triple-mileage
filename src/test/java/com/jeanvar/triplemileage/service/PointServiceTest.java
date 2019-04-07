package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.*;
import com.jeanvar.triplemileage.repository.PointsHistoryRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @InjectMocks
    PointService pointService;

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    PointsHistoryRepository pointsHistoryRepository;

    @Test
    void updatePointsByRegisteringReview() {
        User user = new User();
        Photo photo = new Photo();
        Place place = new Place();

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setContent("aa"); // +1
        review.attachPhotos(Collections.singletonList(photo)); // +1

        when(reviewRepository.existsByPlace(review.getPlace())).thenReturn(false); // +1 for the first review

        pointService.updatePointsByRegisteringReview(user ,review);

        assertThat(user.getPoints()).isEqualTo(3);
        assertThat(review.getPoints()).isEqualTo(3);

        verify(pointsHistoryRepository, times(1)).save(any(PointsHistory.class));
    }

    @Test
    void withdrawPointsByDeletingReview() {
        User user = new User();
        user.setPoints(10);

        Review review = new Review();
        review.setUser(user);
        review.setPoints(3);

        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        pointService.withdrawPointsByDeletingReview(review.getId());

        assertThat(user.getPoints()).isEqualTo(7);

        verify(pointsHistoryRepository, times(1)).save(any(PointsHistory.class));
    }
}