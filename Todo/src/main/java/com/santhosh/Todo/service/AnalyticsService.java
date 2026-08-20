package com.santhosh.Todo.service;

import com.santhosh.Todo.dto.AnalyticsOverviewResponse;
import com.santhosh.Todo.dto.WeeklyAnalyticsResponse;

public interface AnalyticsService {

    AnalyticsOverviewResponse getOverview();

    WeeklyAnalyticsResponse getWeekly();
}
