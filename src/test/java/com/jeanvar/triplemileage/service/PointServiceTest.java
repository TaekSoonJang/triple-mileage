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
    void updatePoint_whenRegisteringReview() {
        User user = new User();
        Photo photo = new Photo();
        Place place = new Place();

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setContent("aa"); // +1
        review.attachPhotos(Collections.singletonList(photo)); // +1

        when(reviewRepository.existsByPlace(review.getPlace())).thenReturn(false); // +1 for the first review

        pointService.updatePointByRegisteringReview(user ,review);

        assertThat(user.getPoints()).isEqualTo(3);

        verify(pointsHistoryRepository, times(1)).save(any(PointsHistory.class));
    }
}