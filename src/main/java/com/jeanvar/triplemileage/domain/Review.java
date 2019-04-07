package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW")
@Getter
@Setter
public class Review extends TripleEntity {
    @Column
    private String content;
}
