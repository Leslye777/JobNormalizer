package com.example.jobnormalizer.controller;

import com.example.jobnormalizer.model.JobTitle;
import com.example.jobnormalizer.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JobTitleController {

    @Autowired
    private JobTitleService jobTitleService;

    @PostMapping("/normalize")
    public ResponseEntity<String> normalizeTitle(@RequestBody JobTitle title) {
        return ResponseEntity.ok(jobTitleService.normalizeTitle(title.getTitle()));
    }
}
