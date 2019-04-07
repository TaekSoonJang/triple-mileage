### Schema Design
Please refer to the [schema.sql](schema.sql).

Index 'review_place' in Table 'review' prevents updating points from scanning full rows
when determining whether this review is the first for this place or not.

### APIs for modifying points
```
void updatePointsByRegisteringReview(user, review) {
  startTransaction()
  
  points = calculatePoints(review)
  
  review.setPoints(points)
  user.points += points
  
  savePointsHistory()
  
  endTransaction()
}
```

```
void updatePointsByModifyingReview(user, review, previousPoints) {
  startTransaction()
  
  points = calculatePoints(review)
  
  diff = points - previousPoints
  
  review.points = points
  user.points += diff
  
  savePointsHistory()
  
  endTransaction()
}
```

```
void withdrawPointsByDeletingReview(review) {
  startTransaction()
  
  user.points -= review.getPoints()
  
  savePointsHistory()
  
  endTransaction()
}
```

```
int calculatePoints(review) {
  points = 0
  
  if (review.content is valid) {
    points += 1
  }
  
  if (review.attachedPhotos is not empty) {
    points += 1
  }
  
  if (review is the first for the place) {
    points += 1
  }
  
  return points
}
```

### API for getting points
```
int getPoints(user) {
  return user.points
}
```