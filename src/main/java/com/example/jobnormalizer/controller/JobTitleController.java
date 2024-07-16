package com.example.jobnormalizer.controller;

import com.example.jobnormalizer.model.JobTitle;
import com.example.jobnormalizer.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JobTitleController {

    @Autowired
    private JobTitleService jobTitleService;

    @PostMapping("/normalize")
    public String normalizeTitle(@RequestBody JobTitle title) {
        return jobTitleService.normalizeTitle(title.getTitle());
    }
}
