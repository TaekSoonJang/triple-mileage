package com.jeanvar.triplemileage.integration;

import com.jeanvar.triplemileage.domain.Photo;
import com.jeanvar.triplemileage.domain.Place;
import com.jeanvar.triplemileage.domain.Review;
import com.jeanvar.triplemileage.domain.User;
import com.jeanvar.triplemileage.repository.PhotoRepository;
import com.jeanvar.triplemileage.repository.PlaceRepository;
import com.jeanvar.triplemileage.repository.UserRepository;
import com.jeanvar.triplemileage.service.PlaceService;
import com.jeanvar.triplemileage.service.PutReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IntegrationTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlaceRepository placeRepository;
    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PlaceService placeService;

    @Test
    void integration() {
        User user1 = new User();
        user1.setName("taeksoon");
        user1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("not taeksoon");
        user2 = userRepository.save(user2);

        Place place1 = new Place();
        place1.setName("new place");
        place1 = placeRepository.save(place1);

        Place place2 = new Place();
        place2.setName("new place 2");
        place2 = placeRepository.save(place2);

        Photo photo = new Photo();
        photo.setUrl("url");
        photo = photoRepository.save(photo);

        // User1 registers Review1 on Place1 (+3 pts)
        PutReview putReview1 = new PutReview();
        putReview1.setUserId(user1.getId());
        putReview1.setPlaceId(place1.getId());
        putReview1.setContent("Liked!");
        putReview1.setAttachedPhotoIds(Collections.singletonList(photo.getId()));

        Review review1 = placeService.registerReview(putReview1);

        user1 = userRepository.findById(user1.getId()).get();
        assertThat(user1.getPoints()).isEqualTo(3);

        // User1 registers Review2 on Place2 (+3 pts)
        PutReview putReview2 = new PutReview();
        putReview2.setUserId(user1.getId());
        putReview2.setPlaceId(place2.getId());
        putReview2.setContent("Liked!");
        putReview2.setAttachedPhotoIds(Collections.singletonList(photo.getId()));

        Review review2 = placeService.registerReview(putReview2);

        user1 = userRepository.findById(user1.getId()).get();
        assertThat(user1.getPoints()).isEqualTo(6);

        // User2 registers Review3 on Place1 (+1 pts)
        PutReview putReview3 = new PutReview();
        putReview3.setUserId(user2.getId());
        putReview3.setPlaceId(place1.getId());
        putReview3.setContent("Liked!");

        Review review3 = placeService.registerReview(putReview3);

        user2 = userRepository.findById(user2.getId()).get();
        assertThat(user2.getPoints()).isEqualTo(1);

        // User1 deletes Review2 on Place2 (- 3pts)
        placeService.deleteReview(review2.getId());

        user1 = userRepository.findById(user1.getId()).get();
        assertThat(user1.getPoints()).isEqualTo(3);

        // User2 registers Review4 on Place 2 (+ 2pts)
        PutReview putReview4 = new PutReview();
        putReview4.setUserId(user2.getId());
        putReview4.setPlaceId(place2.getId());
        putReview4.setContent("Liked!");

        placeService.registerReview(putReview4);

        user2 = userRepository.findById(user2.getId()).get();
        assertThat(user2.getPoints()).isEqualTo(3);

        // User1 modifies Review1 on Place1 (- 1pts)
        PutReview putReview5 = new PutReview();
        putReview5.setUserId(user1.getId());
        putReview5.setPlaceId(place1.getId());
        putReview5.setContent("Liked!!");
        putReview5.setDeletedPhotoIds(Arrays.asList(photo.getId()));

        placeService.modifyReview(review1.getId(), putReview5);

        user1 = userRepository.findById(user1.getId()).get();
        assertThat(user1.getPoints()).isEqualTo(2);
    }
}
