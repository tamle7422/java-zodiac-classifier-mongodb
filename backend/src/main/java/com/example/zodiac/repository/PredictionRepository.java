package com.example.zodiac.repository;

import com.example.zodiac.model.Prediction;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PredictionRepository extends MongoRepository<Prediction, String> {

    List<Prediction> findAllByOrderByTimestampDesc();
}
