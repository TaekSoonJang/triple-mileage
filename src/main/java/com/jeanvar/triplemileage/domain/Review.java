package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
}
