package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "REVIEW_PHOTO")
@Getter
@Setter
public class ReviewPhoto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "REVIEW_ID")
    @ManyToOne
    private Review review;

    @JoinColumn(name = "PHOTO_ID")
    @ManyToOne
    private Photo photo;
}
