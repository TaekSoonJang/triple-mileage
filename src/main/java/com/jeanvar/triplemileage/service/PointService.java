package com.jeanvar.triplemileage.service;

import com.jeanvar.triplemileage.domain.PointsHistory;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import com.jeanvar.triplemileage.repository.PointsHistoryRepository;
import com.jeanvar.triplemileage.repository.ReviewRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class PointService {
    private ReviewRepository reviewRepository;
    private PointsHistoryRepository pointsHistoryRepository;

    public void updatePointsByRegisteringReview(User user, Review review) {
        int pointsAdded = calculatePoints(review);

        review.setPoints(pointsAdded);
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

    public void updatePointsByModifyingReview(User user, Review review, int previousReviewPoints) {
        int modifiedPoints = calculatePoints(review);

        if (previousReviewPoints == modifiedPoints) {
            return;
        }

        int diff = modifiedPoints - previousReviewPoints;

        user.setPoints(user.getPoints() + diff);
        review.setPoints(modifiedPoints);

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPointsChanged(diff);

        String verb = diff > 0 ? "Added" : "Withdrew";
        pointsHistory.setReason(String.format(
            "%s %d points by modifying a review.",
            verb,
            diff
        ));

        pointsHistoryRepository.save(pointsHistory);
    }

    public void withdrawPointsByDeletingReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(RuntimeException::new);
        User user = review.getUser();

        user.setPoints(user.getPoints() - review.getPoints());

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPointsChanged(-review.getPoints());
        pointsHistory.setReason(String.format(
            "Withdrew %d points by deleting a review.",
            review.getPoints()
        ));

        pointsHistoryRepository.save(pointsHistory);
    }

    private int calculatePoints(Review review) {
        int points = 0;

        if (review.getContent().length() > 0) {
            points += 1;
        }

        if (review.getAttachedPhotos().size() > 0) {
            points += 1;
        }

        if (review.isFirst()) {
            points += 1;
        }

        return points;
    }
}
