package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "POINTS_HISTORY")
public class PointsHistory extends TripleEntity {
    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @Column(name = "POINTS_CHANGED")
    private int pointsChanged;

    @Column(name = "REASON")
    private String reason;
}
