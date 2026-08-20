package com.santhosh.Todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.Todo.dto.AnalyticsOverviewResponse;
import com.santhosh.Todo.dto.WeeklyAnalyticsResponse;
import com.santhosh.Todo.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/overview")
    public ResponseEntity<AnalyticsOverviewResponse> getOverview() {
        return ResponseEntity.ok(analyticsService.getOverview());
    }

    @GetMapping("/weekly")
    public ResponseEntity<WeeklyAnalyticsResponse> getWeekly() {
        return ResponseEntity.ok(analyticsService.getWeekly());
    }
}
