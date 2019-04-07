package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PLACE")
@Getter
@Setter
public class Place extends TripleEntity {
    @Column
    private String name;
}
