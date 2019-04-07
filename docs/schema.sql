CREATE TABLE user (
  id BINARY(16) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  points INT
);

CREATE TABLE photo (
  id BINARY(16) PRIMARY KEY,
  url VARCHAR(2000) NOT NULL
);

CREATE TABLE place (
  id BINARY(16) PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE points_history (
  id BINARY(16) PRIMARY KEY,
  points_changed INT,
  reason VARCHAR(1000),
  user_id BINARY(16) NOT NULL,
  FOREIGN KEY points_history_user(user_id) REFERENCES user(id)
);

CREATE TABLE review (
  id BINARY(16) PRIMARY KEY,
  content VARCHAR(1000),
  first BOOLEAN,
  points INT,
  user_id BINARY(16) NOT NULL,
  place_id BINARY(16) NOT NULL,
  FOREIGN KEY review_user(user_id) REFERENCES user(id),
  FOREIGN KEY review_place(place_id) REFERENCES place(id),
  CONSTRAINT review_per_user_and_place UNIQUE(user_id, place_id)
);

CREATE TABLE review_photo (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  photo_id BINARY(16) NOT NULL,
  review_id BINARY(16) NOT NULL,
  FOREIGN KEY review_photo_photo(photo_id) REFERENCES photo(id),
  FOREIGN KEY review_photo_review(review_id) REFERENCES review(id)
);