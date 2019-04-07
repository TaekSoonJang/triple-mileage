package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.Photo;
import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import com.jeanvar.triplemileage.repository.PhotoRepository;
import com.jeanvar.triplemileage.repository.PlaceRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import com.jeanvar.triplemileage.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceServiceTest {
    @InjectMocks
    PlaceService placeService;

    @Mock
    UserRepository userRepository;
    @Mock
    PlaceRepository placeRepository;
    @Mock
    PhotoRepository photoRepository;
    @Mock
    ReviewRepository reviewRepository;

    @Test
    void registerReview() {
        User user = new User();
        Place place = new Place();
        String content = "content";
        Photo p1 = new Photo();
        Photo p2 = new Photo();

        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0, Review.class));

        UUID placeId = place.getId();
        UUID userId = user.getId();
        List<UUID> attachedPhotoIds = Arrays.asList(p1.getId(), p2.getId());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(photoRepository.findAllById(attachedPhotoIds)).thenReturn(Arrays.asList(p1, p2));

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
    }
}