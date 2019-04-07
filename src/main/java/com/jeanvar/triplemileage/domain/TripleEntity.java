package com.jeanvar.triplemileage.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class TripleEntity {
    @Id
    @Setter(AccessLevel.NONE)
    private UUID id = UUID.randomUUID();
}
