package com.jeanvar.triplemileage.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PHOTO")
@Getter
@Setter
public class Photo extends TripleEntity {
    @Column
    private String url;
}
