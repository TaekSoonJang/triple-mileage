package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class User extends TripleEntity {
    @Column
    private String name;

    @Column
    private int points;
}
