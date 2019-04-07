package com.jeanvar.triplemileage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review extends TripleEntity {
    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private Place place;

    @Column
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
