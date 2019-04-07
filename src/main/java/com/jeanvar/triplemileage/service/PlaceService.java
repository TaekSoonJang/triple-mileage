package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.Photo;
import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import com.jeanvar.triplemileage.repository.PhotoRepository;
import com.jeanvar.triplemileage.repository.PlaceRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import com.jeanvar.triplemileage.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PlaceService {
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

        return reviewRepository.save(review);
    }
}
