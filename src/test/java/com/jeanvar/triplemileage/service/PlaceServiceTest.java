package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.*;
import com.jeanvar.triplemileage.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @InjectMocks
    PlaceService placeService;

    @Mock
    PointService pointService;
    @Mock
    UserRepository userRepository;
    @Mock
    PlaceRepository placeRepository;
    @Mock
    PhotoRepository photoRepository;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    PointsHistoryRepository pointsHistoryRepository;

    private User user;
    private Place place;
    private String content;
    private Photo p1;
    private Photo p2;

    @BeforeEach
    void setUp() {
        user = new User();
        place = new Place();
        content = "content";
        p1 = new Photo();
        p2 = new Photo();
    }

    @Test
    void registerReview() {
        UUID placeId = place.getId();
        UUID userId = user.getId();
        List<UUID> attachedPhotoIds = Arrays.asList(p1.getId(), p2.getId());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(photoRepository.findAllById(attachedPhotoIds)).thenReturn(Arrays.asList(p1, p2));
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0, Review.class));

        PutReview putReview = new PutReview();
        putReview.setPlaceId(placeId);
        putReview.setUserId(userId);
        putReview.setContent("content");
        putReview.setAttachedPhotoIds(attachedPhotoIds);

        Review registered = placeService.registerReview(putReview);

        assertThat(registered.getUser()).isEqualTo(user);
        assertThat(registered.getPlace()).isEqualTo(place);
        assertThat(registered.getContent()).isEqualTo(content);
        assertThat(registered.getAttachedPhotos()).containsExactly(p1, p2);

        verify(pointService, times(1))
                .updatePointsByRegisteringReview(eq(user), any(Review.class));
    }

    @Test
    void deleteReview() {
        Review review = new Review();
        UUID reviewId = review.getId();

        placeService.deleteReview(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
        verify(pointService, times(1)).withdrawPointsByDeletingReview(reviewId);
    }

    @Test
    void modifyReview() {
        User user = new User();
        Photo photo = new Photo();

        Review review = new Review();
        review.setUser(user);
        review.setContent("before");
        review.attachPhotos(Collections.singletonList(photo));
        review.setPoints(3);

        PutReview putReview = new PutReview();
        putReview.setContent("after");
        putReview.setDeletedPhotoIds(Collections.singletonList(photo.getId()));

        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        when(photoRepository.findAllById(Collections.singletonList(photo.getId())))
            .thenReturn(Collections.singletonList(photo));

        placeService.modifyReview(review.getId(), putReview);

        assertThat(review.getContent()).isEqualTo("after");
        assertThat(review.getAttachedPhotos()).isEmpty();

        verify(pointService, times(1))
            .updatePointsByModifyingReview(user, review, 3);
    }
}