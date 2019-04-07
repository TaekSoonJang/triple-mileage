package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.*;
import com.jeanvar.triplemileage.repository.PhotoRepository;
import com.jeanvar.triplemileage.repository.PlaceRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import com.jeanvar.triplemileage.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class PlaceService {
    private PointService pointService;
    private UserRepository userRepository;
    private PlaceRepository placeRepository;
    private PhotoRepository photoRepository;
    private ReviewRepository reviewRepository;

    public Review registerReview(PutReview putReview) {
        User user = userRepository.findById(putReview.getUserId()).orElseThrow(RuntimeException::new);
        Place place = placeRepository.findById(putReview.getPlaceId()).orElseThrow(RuntimeException::new);
        List<Photo> attachedPhotos = photoRepository.findAllById(putReview.getAttachedPhotoIds());

        Review review = new Review();
        review.setUser(user);
        review.setPlace(place);
        review.setContent(putReview.getContent());
        review.attachPhotos(attachedPhotos);

        pointService.updatePointsByRegisteringReview(user, review);

        return reviewRepository.save(review);
    }

    public void deleteReview(UUID reviewId) {
        pointService.withdrawPointsByDeletingReview(reviewId);
        reviewRepository.deleteById(reviewId);
    }
}
