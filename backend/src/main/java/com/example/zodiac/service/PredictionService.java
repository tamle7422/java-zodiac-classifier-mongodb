package com.example.zodiac.service;

import com.example.zodiac.model.Prediction;
import com.example.zodiac.repository.PredictionRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private final PredictionRepository repository;

    public PredictionService(PredictionRepository repository) {
        this.repository = repository;
    }

    // ------------------------------
    //       PREDICT ZODIAC
    // ------------------------------
    public String predictZodiac(String keywords) {
        String predictedZodiac = executePythonModel(keywords);

        // Ensure we always have a valid zodiac
        if (predictedZodiac == null || predictedZodiac.isEmpty() || predictedZodiac.equals("Error")) {
            predictedZodiac = "Unknown";
        }

        // Save to MongoDB
        try {
            Prediction p = new Prediction();
            p.setKeywords(keywords);
            p.setPredictedZodiac(predictedZodiac);
            p.setTimestamp(LocalDateTime.now());

            repository.save(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return predictedZodiac;
    }

    // ------------------------------
    //   CALL PYTHON PREDICT SCRIPT
    // ------------------------------
    private String executePythonModel(String text) {
        try {
            String safeText = "\"" + text + "\"";

            ProcessBuilder pb = new ProcessBuilder(
                    "python3",
                    "/home/le/Desktop/programs/java/zodiac-classifier-mongodb/predict_model.py",
                    safeText
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            List<String> output = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }

            process.waitFor();

            if (output.isEmpty()) {
                return "Unknown";
            }

            return output.get(output.size() - 1).trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

    // ------------------------------
    //         HISTORY LIST
    // ------------------------------
    public List<Prediction> getAllPredictions() {
        try {
            List<Prediction> all = repository.findAllByOrderByTimestampDesc();
            return all != null ? all : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ------------------------------
    //         CLEAR HISTORY
    // ------------------------------
    public void clearHistory() {
        try {
            repository.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
