package com.example.zodiac.controller;

import com.example.zodiac.model.Prediction;
import com.example.zodiac.service.PredictionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ZodiacController {

    private final PredictionService service;

    public ZodiacController(PredictionService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/predict")
    public String predict(@RequestParam("keywords") String keywords, Model model) {
        String zodiac = service.predictZodiac(keywords);
        model.addAttribute("prediction", zodiac);
        model.addAttribute("keywords", keywords);
        return "index";
    }

    @GetMapping("/history")
public String history(Model model) {
    try {
        List<Prediction> records = service.getAllPredictions();
        System.out.println("History list size: " + records.size());
        records.forEach(r -> System.out.println(
            "KW: " + r.getKeywords() + ", Zodiac: " + r.getPredictedZodiac() + ", TS: " + r.getTimestamp()
        ));
        model.addAttribute("records", records);
        return "history";
    } catch (Exception e) {
        e.printStackTrace(); // prints full stack trace
        model.addAttribute("records", new ArrayList<>());
        return "history"; // still render page safely
    }
}


}
