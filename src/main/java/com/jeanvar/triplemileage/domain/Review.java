package com.jeanvar.triplemileage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(
        name = "REVIEW",
        uniqueConstraints = @UniqueConstraint(
                name = "ONE_REVIEW_PER_USER_AND_PLACE",
                columnNames = {"USER_ID", "PLACE_ID"}
        )
)
public class Review extends TripleEntity {
    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "PLACE_ID")
    @ManyToOne
    private Place place;

    @Column(name = "CONTENT")
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter(AccessLevel.NONE)
    private List<ReviewPhoto> attachedPhotos = new ArrayList<>();

    public void attachPhotos(List<Photo> attachedPhotos) {
        List<ReviewPhoto> reviewPhotos = attachedPhotos.stream()
                .map(p -> {
                    ReviewPhoto reviewPhoto = new ReviewPhoto();
                    reviewPhoto.setReview(this);
                    reviewPhoto.setPhoto(p);

                    return reviewPhoto;
                })
                .collect(Collectors.toList());

        this.attachedPhotos.addAll(reviewPhotos);
    }

    public List<Photo> getAttachedPhotos() {
        return attachedPhotos.stream()
                .map(ReviewPhoto::getPhoto)
                .collect(Collectors.toList());
    }
}


