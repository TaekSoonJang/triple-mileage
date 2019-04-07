package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.*;
import com.jeanvar.triplemileage.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class PlaceService {
    private PointsService pointsService;
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
        review.setFirst(!reviewRepository.existsByPlace(place));

        pointsService.updatePointsByRegisteringReview(user, review);

        return reviewRepository.save(review);
    }

    public void deleteReview(UUID reviewId) {
        pointsService.withdrawPointsByDeletingReview(reviewId);
        reviewRepository.deleteById(reviewId);
    }

    public void modifyReview(UUID reviewId, PutReview putReview) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        List<Photo> deletingPhotos = photoRepository.findAllById(putReview.getDeletedPhotoIds());
        int previousPoints = review.getPoints();

        review.setContent(putReview.getContent());
        review.deleteAttachedPhotos(deletingPhotos);

        pointsService.updatePointsByModifyingReview(review.getUser(), review, previousPoints);
    }
}
