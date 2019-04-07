package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.PointsHistory;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import com.jeanvar.triplemileage.repository.PointsHistoryRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PointService {
    private ReviewRepository reviewRepository;
    private PointsHistoryRepository pointsHistoryRepository;

    public void updatePointByRegisteringReview(User user, Review review) {
        int pointsAdded = 0;

        if (review.getContent().length() > 0) {
            pointsAdded += 1;
        }

        if (review.getAttachedPhotos().size() > 0) {
            pointsAdded += 1;
        }

        if (!reviewRepository.existsByPlace(review.getPlace())) {
            pointsAdded += 1;
        }

        user.setPoints(user.getPoints() + pointsAdded);

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPointsChanged(pointsAdded);
        pointsHistory.setReason(String.format(
            "Added %d points by registering a review.",
            pointsAdded
        ));

        pointsHistoryRepository.save(pointsHistory);
    }
}
