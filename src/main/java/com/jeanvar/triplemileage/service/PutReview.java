package com.jeanvar.triplemileage.service;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PutReview {
    private UUID placeId;
    private UUID userId;
    private String content = "";
    private List<UUID> attachedPhotoIds = new ArrayList<>();
    private List<UUID> deletedPhotoIds = new ArrayList<>();
}
