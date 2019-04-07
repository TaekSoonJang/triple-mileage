package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW_PHOTO")
@Getter
@Setter
public class ReviewPhoto extends TripleEntity {
    @JoinColumn(name = "REVIEW_ID")
    @ManyToOne
    private Review review;

    @JoinColumn(name = "PHOTO")
    @ManyToOne
    private Photo photo;
}
